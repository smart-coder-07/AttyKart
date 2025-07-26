package com.gateway.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.gateway.filter.JwtAuthenticationFilter;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
            .csrf(csrf -> csrf.disable())
            .authorizeExchange(exchange -> exchange

                // Public access (e.g., registration & login)
                .pathMatchers("/userservice/auth/**").permitAll()

                // ProductService
                .pathMatchers(HttpMethod.GET, "/productservice/**").hasAnyRole("ROLE_USER", "ROLE_SHOPEKEEPER", "ROLE_ADMIN")
                .pathMatchers(HttpMethod.POST, "/productservice/**").hasAnyRole("ROLE_SHOPEKEEPER", "ROLE_ADMIN")
                .pathMatchers(HttpMethod.PUT, "/productservice/**").hasAnyRole("ROLE_SHOPEKEEPER", "ROLE_ADMIN")
                .pathMatchers(HttpMethod.DELETE, "/productservice/**").hasRole("ROLE_ADMIN")

                // CartService
                .pathMatchers("/cartservice/**").hasRole("ROLE_USER")

                // OrderService
                .pathMatchers("/orderservice/**").hasRole("ROLE_USER")

                // TransactionService
                .pathMatchers(HttpMethod.GET, "/transactionservice/all").hasRole("ROLE_ADMIN")
                .pathMatchers("/transactionservice/**").hasRole("ROLE_USER")

                // WalletService (optional)
                .pathMatchers("/walletservice/**").hasRole("ROLE_USER")

                // NotificationService (internal, deny access from outside)
                .pathMatchers("/notificationservice/**").denyAll()

                // Everything else must be authenticated
                .anyExchange().authenticated()
            )
            .addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
            .build();
    }


}
