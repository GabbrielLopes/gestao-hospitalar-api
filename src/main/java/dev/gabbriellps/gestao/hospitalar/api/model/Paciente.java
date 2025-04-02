package dev.gabbriellps.gestao.hospitalar.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@SequenceGenerator(name = "PACIENTE_SEQ", sequenceName = "PACIENTE_SEQ", allocationSize = 1)
@Table(name = "PACIENTE")
public class Paciente {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PACIENTE_SEQ")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PESSOA", referencedColumnName = "ID", nullable = false)
    private Pessoa pessoa;

    @Column(name = "HISTORICO_CLINICO")
    private String historicoClinico;

    @Column(name = "ATIVO", nullable = false)
    private Boolean ativo;

    @OneToMany(mappedBy = "paciente", fetch = FetchType.LAZY)
    private List<Consulta> consultas;

    @OneToMany(mappedBy = "paciente", fetch = FetchType.LAZY)
    private List<Exame> exames;


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
