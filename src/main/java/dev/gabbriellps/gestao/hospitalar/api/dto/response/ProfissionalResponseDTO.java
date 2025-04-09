package dev.gabbriellps.gestao.hospitalar.api.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.gabbriellps.gestao.hospitalar.api.enumeration.Especialidade;
import dev.gabbriellps.gestao.hospitalar.api.model.Pessoa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfissionalResponseDTO {

    private Long id;
    private Pessoa pessoa;
    private Especialidade especialidade;
    private String registroProfissional;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataCriacao;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataAtualizacao;

}
