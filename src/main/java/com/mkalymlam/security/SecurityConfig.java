package com.mkalymlam.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Pages publiques
                .requestMatchers("/login", "/register", "/error", "/favicon.ico").permitAll()
                // Ressources statiques
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                // 👇 PERMET L'ACCÈS AUX VUES JSP (forwards internes)
                .requestMatchers("/WEB-INF/views/**").permitAll()
                // Dashboard protégé
                .requestMatchers("/dashboard").authenticated()
                // Tout le reste nécessite authentification
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/dashboard", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            .csrf(csrf -> csrf.disable()); // à réactiver plus tard avec les tokens

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}