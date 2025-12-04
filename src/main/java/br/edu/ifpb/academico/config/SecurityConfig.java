package br.edu.ifpb.academico.config;

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
                // Recursos estáticos liberados
                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()

                // Página inicial
                .requestMatchers("/", "/paginaPrincipal").authenticated()

                // Rotas do sistema (no momento ainda sem segurança real)
                .requestMatchers("/motorista/**").permitAll()
                .requestMatchers("/viagem/**").permitAll()

                // Quando criarmos usuários e roles, trocaremos isso aqui:
                .anyRequest().authenticated()
            )

            // Login ainda não implementado, então deixamos neutro:
            .formLogin(form -> form
                .loginPage("/login")   // quando você criar essa rota
                .permitAll()
            )

            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
