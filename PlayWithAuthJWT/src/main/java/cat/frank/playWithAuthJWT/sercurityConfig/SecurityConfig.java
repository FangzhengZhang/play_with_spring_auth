package cat.frank.playWithAuthJWT.sercurityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import javax.management.MXBean;

@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                //.antMatchers(HttpMethod.GET).permitAll() //this is used to allowed visitor pass
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
        return http.build();

    }
}
