package dev.gabbriellps.gestao.hospitalar.api.model;

import dev.gabbriellps.gestao.hospitalar.api.dto.request.ProfissionalSaudeRequestDTO;
import dev.gabbriellps.gestao.hospitalar.api.dto.response.ProfissionalSaudeResponseDTO;
import dev.gabbriellps.gestao.hospitalar.api.enumeration.Especialidade;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

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

    @Column(name = "ATIVO", nullable = false)
    private Boolean ativo;

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
        ativo = true;
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


    public static ProfissionalSaudeResponseDTO mapToProfissionalSaudeResponseDTO(ProfissionalSaude profissionalSaude) {
        return new ModelMapper().map(profissionalSaude, ProfissionalSaudeResponseDTO.class);
    }

    public ProfissionalSaudeResponseDTO toProfissionalSaudeResponseDTO() {
        return ProfissionalSaudeResponseDTO.builder()
                .id(id)
                .pessoa(pessoa)
                .especialidade(especialidade)
                .registroProfissional(registroProfissional)
                .dataCriacao(dataCriacao)
                .dataAtualizacao(dataAtualizacao)
                .build();
    }

    public void atualizaDadosPessoa(ProfissionalSaudeRequestDTO requestDTO) {
        pessoa.setNome(requestDTO.getPessoa().getNome());
        pessoa.setSexo(requestDTO.getPessoa().getSexo());
        pessoa.setDataNascimento(requestDTO.getPessoa().getDataNascimento());
        pessoa.setCpf(requestDTO.getPessoa().getCpf());
        pessoa.setCep(requestDTO.getPessoa().getCep());
        pessoa.setEndereco(requestDTO.getPessoa().getEndereco());
        pessoa.setComplemento(requestDTO.getPessoa().getComplemento());
        pessoa.setTelefone(requestDTO.getPessoa().getTelefone());
        pessoa.setEmail(requestDTO.getPessoa().getEmail());
    }

    public void inativar() {
        ativo = false;
    }

    public void ativar() {
        ativo = true;
    }

}
