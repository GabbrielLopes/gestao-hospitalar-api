package dev.gabbriellps.gestao.hospitalar.api.model;

import dev.gabbriellps.gestao.hospitalar.api.enumeration.TipoConsulta;
import dev.gabbriellps.gestao.hospitalar.api.enumeration.TipoExame;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

import static jakarta.persistence.EnumType.STRING;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@SequenceGenerator(name = "EXAME_SEQ", sequenceName = "EXAME_SEQ", allocationSize = 1)
@Table(name = "EXAME")
public class Exame {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EXAME_SEQ")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PACIENTE", referencedColumnName = "ID", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PROFISSIONAL", referencedColumnName = "ID", nullable = false)
    private ProfissionalSaude profissionalSaude;

    @Column(name = "DTHR_EXAME", nullable = false)
    private LocalDateTime dataHoraExame;

    @Column(name = "TIPO", length = 50, nullable = false)
    @Enumerated(STRING)
    private TipoExame tipo;

    @Column(name = "RESULTADO")
    private String resultado;


    @Column(name = "DTHR_CRIACAO", nullable = false)
    private LocalDateTime dataCriacao;
    @Column(name = "DTHR_ATUALIZACAO")
    private LocalDateTime dataAtualizacao;

    @PrePersist
    protected void onCreate() {
        if (Objects.isNull(dataCriacao)) {
            dataCriacao = LocalDateTime.now();
        }
        dataAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }


}
