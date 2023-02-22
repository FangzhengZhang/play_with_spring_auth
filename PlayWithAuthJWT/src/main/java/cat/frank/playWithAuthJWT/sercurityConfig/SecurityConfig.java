package cat.frank.playWithAuthJWT.sercurityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.management.MXBean;

@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> {
                    try {
                        authorize
                                .requestMatchers("/test/**", "/signup", "/about").permitAll()
                                .requestMatchers(HttpMethod.GET).permitAll()
                                .anyRequest().authenticated()
                                .and()
                                .httpBasic();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        );
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
     * Create default admin and user, so in the initial run, we do not need database but still able to use the service.
     * @return
     */
    @Bean
    public UserDetailsService users(){
        UserDetails admin = User.builder()
                .username("admin") // default username
                .password("password") // default password
                .roles("ADMIN")
                .build();

        UserDetails user = User.builder()
                .username("user") // default username
                .password("password") // default password
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(admin, user);
    }
}
