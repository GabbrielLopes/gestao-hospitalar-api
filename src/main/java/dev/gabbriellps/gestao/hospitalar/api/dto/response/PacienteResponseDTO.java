package dev.gabbriellps.gestao.hospitalar.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.gabbriellps.gestao.hospitalar.api.model.Consulta;
import dev.gabbriellps.gestao.hospitalar.api.model.Exame;
import dev.gabbriellps.gestao.hospitalar.api.model.Pessoa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_EMPTY)
public class PacienteResponseDTO {

    private Long id;
    private PessoaResponseDTO pessoa;
    private String historicoClinico;
    private Boolean ativo;
    private List<Consulta> consultas;
    private List<Exame> exames;


    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

}
