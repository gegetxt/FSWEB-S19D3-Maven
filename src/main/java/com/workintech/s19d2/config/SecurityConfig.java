package com.workintech.s19d2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/register", "/workintech/auth/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/account", "/account/**", "/workintech/accounts", "/workintech/accounts/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/account", "/account/**", "/workintech/accounts", "/workintech/accounts/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/account/**", "/workintech/accounts/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/account/**", "/workintech/accounts/**").hasAuthority("ADMIN")
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .oauth2Login(Customizer.withDefaults())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
