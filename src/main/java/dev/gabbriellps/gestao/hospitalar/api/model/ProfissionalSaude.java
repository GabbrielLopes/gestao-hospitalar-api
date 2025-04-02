package dev.gabbriellps.gestao.hospitalar.api.model;

import dev.gabbriellps.gestao.hospitalar.api.enumeration.Especialidade;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static jakarta.persistence.EnumType.STRING;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@SequenceGenerator(name = "PROFISSIONAL_SAUDE_SEQ", sequenceName = "PROFISSIONAL_SAUDE_SEQ", allocationSize = 1)
@Table(name = "PROFISSIONAL_SAUDE")
public class ProfissionalSaude {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROFISSIONAL_SAUDE_SEQ")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PESSOA", referencedColumnName = "ID", nullable = false)
    private Pessoa pessoa;

    @Column(name = "ESPECIALIDADE", length = 100)
    @Enumerated(STRING)
    private Especialidade especialidade;

    @Column(name = "REGISTRO_PROFISSIONAL", length = 30)
    private String registroProfissional;

    @OneToMany(mappedBy = "profissionalSaude", fetch = FetchType.LAZY)
    private List<Consulta> consultas;

    @OneToMany(mappedBy = "profissionalSaude", fetch = FetchType.LAZY)
    private List<Exame> exames;

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
