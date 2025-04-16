package dev.gabbriellps.gestao.hospitalar.api.configuration.security;

import dev.gabbriellps.gestao.hospitalar.api.handler.VidaPlusServiceException;
import dev.gabbriellps.gestao.hospitalar.api.model.Usuario;
import dev.gabbriellps.gestao.hospitalar.api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityHelper {

    private final UsuarioRepository repository;

    public Usuario getUserLogado() throws VidaPlusServiceException {
        try {
            String nome = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

            return repository.buscaPeloLogin(nome)
                    .orElseThrow(() -> new VidaPlusServiceException("Usuário não encontrado", HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new VidaPlusServiceException("Erro ao obter usuário logado", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
