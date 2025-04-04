package com.company.test.config;

import com.company.test.security.JwtAuthFilter;
import com.company.test.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;
    private final JwtAuthFilter jwtAuthFilter;

    // Настройка AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authenticationProvider());
        return authenticationManagerBuilder.build();
    }

    // Настройка AuthenticationProvider для работы с UserService и паролями
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService); // Теперь UserService реализует UserDetailsService
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // Bean для кодировщика паролей
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Настройка HttpSecurity
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/auth/**").permitAll() // Публичные эндпоинты
                                .requestMatchers(HttpMethod.GET, "/api/cards/**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/cards/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/cards/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/cards/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/transactions/**").hasAnyRole("USER", "ADMIN")
                                .anyRequest().authenticated() // Все остальные запросы требуют аутентификации
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
