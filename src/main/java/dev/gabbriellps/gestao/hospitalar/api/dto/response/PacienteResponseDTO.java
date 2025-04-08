package dev.gabbriellps.gestao.hospitalar.api.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private Boolean ativo;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataCriacao;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataAtualizacao;

}
