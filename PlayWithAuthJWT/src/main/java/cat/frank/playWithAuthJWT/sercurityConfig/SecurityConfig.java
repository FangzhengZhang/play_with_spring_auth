package cat.frank.playWithAuthJWT.sercurityConfig;

import cat.frank.playWithAuthJWT.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.management.MXBean;
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private JwtAuthEntryPoint jwtAuthEntryPoint;
    private CustomUserDetailsService userDetailsService;
    @Autowired
    public SecurityConfig(CustomUserDetailsService userDetailsService, JwtAuthEntryPoint jwtAuthEntryPoint) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthEntryPoint = jwtAuthEntryPoint;
    }

    //https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // add the jwtAuthEntryPoint to the filter chain
        // if the JWT passed, then you do not need user and password
        // In JWTAuthenticationFilter.java. It obtains JWT from header.
        http
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthEntryPoint)
                .and()
                // Since we bring in JWT,so we need to disable session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeHttpRequests(authorize -> {
                    try {
                        authorize
                                .requestMatchers("/api/auth/test/**", "/api/auth/signup/**", "/about").permitAll()
                                .requestMatchers("/api/auth/register/**",
                                        "/api/auth/login/**").permitAll()
                                .requestMatchers("/api/auth/checkUserRecords/**").authenticated()
                                .anyRequest().denyAll()
                                .and()
                                .csrf().disable();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        // Apply jwtAuthenticationFilter that we created here
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }

    /**
     * @Bean
     * SecurityFilterChain web(HttpSecurity http) throws Exception {
     * 	http
     * 		// ...
     * 		.authorizeHttpRequests(authorize -> authorize
     * 			.requestMatchers("/resources/**", "/signup", "/about").permitAll()
     * 			.requestMatchers("/admin/**").hasRole("ADMIN")
     * 			.requestMatchers("/db/**").access(new WebExpressionAuthorizationManager("hasRole('ADMIN') and hasRole('DBA')"))
     * 			// .requestMatchers("/db/**").access(AuthorizationManagers.allOf(AuthorityAuthorizationManager.hasRole("ADMIN"), AuthorityAuthorizationManager.hasRole("DBA")))
     * 			.anyRequest().denyAll()
     * 		);
     *
     * 	return http.build();
     * }
     */


    /**
     * Setup Authentication manager
     * @param authenticationConfiguration
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Setup password encoder
     */
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter(){
        return new JWTAuthenticationFilter();
    }
}
