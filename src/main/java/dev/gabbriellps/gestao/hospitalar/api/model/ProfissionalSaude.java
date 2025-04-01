package dev.gabbriellps.gestao.hospitalar.api.model;

import dev.gabbriellps.gestao.hospitalar.api.enumeration.Especialidade;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

import static jakarta.persistence.EnumType.STRING;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "PROFISSIONAL_SAUDE")
public class ProfissionalSaude {

    @Id
    @Column(name = "ID", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PROFISSIONAL_SAUDE", referencedColumnName = "ID", nullable = false)
    private Pessoa pessoa;

    @Column(name = "ESPECIALIDADE", length = 100)
    @Enumerated(STRING)
    private Especialidade especialidade;

    @Column(name = "REGISTRO_PROFISSIONAL", length = 30)
    private String registroProfissional;

    // Implementar Agenda em breve


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
        if (Objects.nonNull(dataCriacao)) {
            dataAtualizacao = LocalDateTime.now();
        }
    }



}
