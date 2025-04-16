package dev.gabbriellps.gestao.hospitalar.api.configuration.security;

import dev.gabbriellps.gestao.hospitalar.api.handler.ErrorResponse;
import dev.gabbriellps.gestao.hospitalar.api.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        HttpServletResponse res = (HttpServletResponse) response;

        String token = getToken(request);

        if (Objects.nonNull(token)) {
            String login = tokenService.validaToken(token);
            UserDetails usuario = usuarioRepository.findByLogin(login);
            validaUsuarioInvalido(usuario, res);

            var authentication = new UsernamePasswordAuthenticationToken(
                    login, null, usuario.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

        }
        filterChain.doFilter(request, response);
    }

    private static void validaUsuarioInvalido(UserDetails usuario, HttpServletResponse res) throws IOException {
        if (Objects.isNull(usuario)) {
            res.resetBuffer();
            res.setStatus(HttpStatus.UNAUTHORIZED.value());
            res.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            String retorno = new ErrorResponse("Usuario não autenticado", HttpStatus.UNAUTHORIZED).toString();
            res.getOutputStream().print(retorno);
            res.flushBuffer();
        }
    }

    private static String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (Objects.isNull(token)) {
            return null;
        }

        return token.replace("Bearer ", "");
    }
}
