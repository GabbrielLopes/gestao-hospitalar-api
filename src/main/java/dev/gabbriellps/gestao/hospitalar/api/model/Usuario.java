package dev.gabbriellps.gestao.hospitalar.api.model;

import dev.gabbriellps.gestao.hospitalar.api.enumeration.TipoUsuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@SequenceGenerator(name = "USUARIO_SEQ", sequenceName = "USUARIO_SEQ", allocationSize = 1)
@Table(name = "USUARIO")
public class Usuario {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USUARIO_SEQ")
    private Long id;

    @Column(name = "TIPO_USUARIO", length = 50, nullable = false)
    private TipoUsuario tipoUsuario;

    @Column(name = "ATIVO", nullable = false)
    private Boolean ativo;

    @Column(name = "DATA_HORA", nullable = false)
    private LocalDateTime dataHora;

    @Column(name = "DT_CRIACAO", nullable = false)
    private LocalDateTime dataCriacao;


    @PrePersist
    protected void onCreate() {
        if (Objects.isNull(dataCriacao)) {
            dataCriacao = LocalDateTime.now();
        }
        dataHora = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dataHora = LocalDateTime.now();
    }


    // todo remover depois, metodo usado para teste
    public static Usuario getUserAcao() {
        return Usuario.builder()
                .id(1L)
                .tipoUsuario(TipoUsuario.ADMIN)
                .ativo(Boolean.TRUE)
                .dataHora(LocalDateTime.now())
                .dataCriacao(LocalDateTime.now())
                .build();
    }

}
