package dev.gabbriellps.gestao.hospitalar.api.repository;

import dev.gabbriellps.gestao.hospitalar.api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    UserDetails findByLogin(String login);

    @Query("SELECT u FROM Usuario u WHERE u.login = :login")
    Optional<Usuario> buscaPeloLogin(@Param("login") String login);

}
