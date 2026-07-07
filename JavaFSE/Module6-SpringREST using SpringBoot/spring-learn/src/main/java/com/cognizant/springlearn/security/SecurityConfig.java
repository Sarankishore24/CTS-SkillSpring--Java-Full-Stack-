package com.cognizant.springlearn.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security configuration.
 *
 * - Two in-memory users: admin (role ADMIN) and user (role USER)
 * - /authenticate requires USER or ADMIN role (HTTP Basic)
 * - All other requests require a valid JWT Bearer token
 * - Stateless session (no cookies / JSESSIONID)
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public PasswordEncoder passwordEncoder() {
        LOGGER.info("Start");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(
            User.withUsername("admin")
                .password(passwordEncoder().encode("pwd"))
                .roles("ADMIN")
                .build()
        );
        manager.createUser(
            User.withUsername("user")
                .password(passwordEncoder().encode("pwd"))
                .roles("USER")
                .build()
        );
        return manager;
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManager authManager = authenticationManager();

        http
            .csrf(csrf -> csrf.disable())
            // Enable HTTP Basic so /authenticate can be called with -u user:pwd
            .httpBasic(httpBasic -> {})
            // Stateless — no server-side session
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // /authenticate is accessible by USER or ADMIN via HTTP Basic
                .requestMatchers("/authenticate").hasAnyRole("USER", "ADMIN")
                // All other endpoints require a valid JWT
                .anyRequest().authenticated()
            )
            // Plug in our JWT Bearer token filter
            .addFilter(new JwtAuthorizationFilter(authManager));

        return http.build();
    }
}
