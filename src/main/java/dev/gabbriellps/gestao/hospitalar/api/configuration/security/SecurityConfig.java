package dev.gabbriellps.gestao.hospitalar.api.configuration.security;

import lombok.RequiredArgsConstructor;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final SecurityFilter securityFilter;

    @Bean // Configuração de segurança
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/profissionais-saude/**").hasAnyRole("ADMIN", "TECNICO_ADM")
                        .requestMatchers(HttpMethod.POST, "/pacientes/**").hasAnyRole("ADMIN", "TECNICO_ADM")
                        .requestMatchers(HttpMethod.POST, "/exames/**").hasAnyRole("ADMIN", "TECNICO_ADM")
                        .requestMatchers(HttpMethod.POST, "/consultas/**").hasAnyRole("ADMIN", "TECNICO_ADM")
                        .requestMatchers(getUrlIgnoradas()).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
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


    protected String[] getUrlIgnoradas() {
        return new String[] {
                "/health-check",
                "/swagger-ui/**",
                "/v3/**"
        };
    }
}
