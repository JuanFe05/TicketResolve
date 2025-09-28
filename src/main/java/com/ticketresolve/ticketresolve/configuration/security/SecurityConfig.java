package com.ticketresolve.ticketresolve.configuration.security;

import com.ticketresolve.ticketresolve.configuration.security.filters.JwtAuthenticationFilter;
import com.ticketresolve.ticketresolve.configuration.security.filters.JwtAuthorizationFilter;
import com.ticketresolve.ticketresolve.configuration.security.jwt.JwtUtils;
import com.ticketresolve.ticketresolve.service.implementation.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    UserDetailServiceImpl userDetailsService;

    @Autowired
    JwtAuthorizationFilter authorizationFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                            AuthenticationManager authenticationManager) throws Exception {

        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtils);
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);

        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/usuarios/login").permitAll()
                        .requestMatchers("/usuarios/createUsuario").hasAuthority("ADMINISTRADOR") // arriba agregamos ROLE_
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilter(jwtAuthenticationFilter)
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}