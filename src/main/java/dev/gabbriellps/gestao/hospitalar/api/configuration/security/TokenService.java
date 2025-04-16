package dev.gabbriellps.gestao.hospitalar.api.configuration.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import dev.gabbriellps.gestao.hospitalar.api.model.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Slf4j
@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    public String geraToken(Usuario usuario){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(getExpiration())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            log.error("Erro ao gerar token: {}", exception.getMessage());
            throw new RuntimeException("Error ao gerar token", exception);
        }
    }

    public String validaToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            return "";
        }
    }

    private Instant getExpiration(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}