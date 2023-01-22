package com.nibado.example.spring.secsingleuser;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/*
	Configures a single in-memory user that is used to protect all the views. Presents a standard Spring
	login screen.

	The username and password are retrieved from the service.login property. This needs to contain a username and
	bcrypt hashed password. These pairs can easily be created using the standard htpasswd utility:

	$ htpasswd -nbBC 10 user topsecret
	> user:$2y$10$KHKKFNs.AbII3HW.OMY8c.7nLHmOZ/cbxqmbFF/CVpymbVVp4YkCS
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Value("${service.login}")
    private String login;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
            .requestMatchers("/actuator/health", "/api").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin(withDefaults())
            .httpBasic(withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        var userNamePassword = login.split(":", 2);
        var userDetails = User.builder()
            .username(userNamePassword[0])
            .password("{bcrypt}" + userNamePassword[1]).roles("USER").build();

        return new InMemoryUserDetailsManager(userDetails);
    }
}
