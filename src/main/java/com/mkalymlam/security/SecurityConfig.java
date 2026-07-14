package com.mkalymlam.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

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

                // ================= ROUTES RÉSERVÉES À L'ADMIN =================
                .requestMatchers(
                        // Dashboard & statistiques
                        "/dashboard", "/dashboard-statistique", "/dashboard/**",
                        "/statistique", "/statistiques", "/statistique/**", "/statistiques/**",
                        // Gestion RH
                        "/clients/**",
                        "/employes/**",
                        "/conges/**",
                        "/fiches-paie/**",
                        // Avis / retours clients
                        "/retour/**",
                        // Équipe & équipements
                        "/equipe/**",
                        "/equipements/**",
                        "/equipement/**"
                ).hasRole("ADMIN")

                // Tout le reste nécessite simplement d'être authentifié
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                // Redirection selon le rôle après connexion
                .successHandler(authenticationSuccessHandler())
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            .csrf(csrf -> csrf.disable()); // à réactiver plus tard avec les tokens

        return http.build();
    }

    /**
     * Après connexion, l'admin est redirigé vers le dashboard,
     * les autres utilisateurs (employés, etc.) vers leur espace employé.
     */
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()));
            String target = isAdmin ? "/dashboard" : "/inventaire/findAll";
            response.sendRedirect(request.getContextPath() + target);
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
