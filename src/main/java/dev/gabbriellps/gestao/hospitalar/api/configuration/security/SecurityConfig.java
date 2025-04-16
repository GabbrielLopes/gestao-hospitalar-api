package dev.gabbriellps.gestao.hospitalar.api.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean // Configuração de segurança
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/vida-plus/profissionais/**").hasAnyRole("ADMIN", "TECNICO_ADM")
                        .requestMatchers(HttpMethod.POST, "/vida-plus/pacientes/**").hasAnyRole("ADMIN", "TECNICO_ADM")
                        .anyRequest().authenticated()
                )
                .build();
    }

    @Bean // Gerenciador de autenticação
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean // Encodar e decodar senhas
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
