package dev.gabbriellps.gestao.hospitalar.api.model;

import dev.gabbriellps.gestao.hospitalar.api.dto.response.ConsultaResponseDTO;
import dev.gabbriellps.gestao.hospitalar.api.enumeration.TipoConsulta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Objects;

import static jakarta.persistence.EnumType.STRING;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@SequenceGenerator(name = "CONSULTA_SEQ", sequenceName = "CONSULTA_SEQ", allocationSize = 1)
@Table(name = "CONSULTA")
public class Consulta {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONSULTA_SEQ")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PACIENTE", referencedColumnName = "ID", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PROFISSIONAL", referencedColumnName = "ID", nullable = false)
    private ProfissionalSaude profissionalSaude;

    @Column(name = "DTHR_CONSULTA", nullable = false)
    private LocalDateTime dataHoraConsulta;

    @Column(name = "TIPO", length = 50, nullable = false)
    @Enumerated(STRING)
    private TipoConsulta tipo;

    @Column(name = "PRONTUARIO")
    private String prontuario;

    @Column(name = "RECEITA_DIGITAL")
    private String receitaDigital;

    @Column(name = "TELECONSULTA", nullable = false)
    private Boolean teleconsulta;


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

    public static ConsultaResponseDTO mapToConsultaResponseDTO(Consulta consulta) {
        return new ModelMapper().map(consulta, ConsultaResponseDTO.class);
    }

    public ConsultaResponseDTO toConsultaResponseDTO() {
        return new ModelMapper().map(this, ConsultaResponseDTO.class);
    }

}
