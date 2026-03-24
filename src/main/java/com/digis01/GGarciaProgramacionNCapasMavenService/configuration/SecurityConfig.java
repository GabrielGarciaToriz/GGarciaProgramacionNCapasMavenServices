package com.digis01.GGarciaProgramacionNCapasMavenService.configuration;

import com.digis01.GGarciaProgramacionNCapasMavenService.security.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, AuthenticationProvider authenticationProvider) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/api/auth/**",
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html"
                ).permitAll()
                .requestMatchers(HttpMethod.GET, "/api/usuario", "/api/usuario/*").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/usuario/buscar").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/direccion/me", "/api/direccion/me/**", "/api/direccion/*").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/direccion/me").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/direccion/me/*").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/api/direccion/me/*").authenticated()
                .anyRequest().hasAuthority("Administrador")
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(exception -> exception
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN); // Código 403
                    response.setContentType("application/json;charset=UTF-8"); // Para que acepte acentos
                    response.getWriter().write("{\"status\": 403, \"message\": \"No tienes los permisos necesarios para ejecutar este endpoint.\"}");
                })
            );

        return http.build();
    }
}