package dev.gabbriellps.gestao.hospitalar.api.model;

import dev.gabbriellps.gestao.hospitalar.api.enumeration.TipoUsuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@SequenceGenerator(name = "USUARIO_SEQ", sequenceName = "USUARIO_SEQ", allocationSize = 1)
@Table(name = "USUARIO")
public class Usuario implements UserDetails {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USUARIO_SEQ")
    private Long id;

    @Column(name = "LOGIN", length = 50, nullable = false)
    private String login;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "ROLE", length = 50, nullable = false)
    @Enumerated(STRING)
    private TipoUsuario role;

    @Column(name = "DT_CRIACAO", nullable = false)
    private LocalDateTime dataCriacao;


    public Usuario(String login, String password, TipoUsuario role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
    }


    // todo remover depois, metodo usado para teste
    public static Usuario getUserAcao() {
        return Usuario.builder()
                .id(1L)
                .role(dev.gabbriellps.gestao.hospitalar.api.enumeration.TipoUsuario.ADMIN)
                .dataCriacao(LocalDateTime.now())
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(TipoUsuario.ADMIN.equals(this.role)) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
