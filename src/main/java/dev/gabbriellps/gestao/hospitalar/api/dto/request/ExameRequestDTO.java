package dev.gabbriellps.gestao.hospitalar.api.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.gabbriellps.gestao.hospitalar.api.enumeration.TipoExame;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExameRequestDTO {

    @NotNull(message = "O campo pacienteId é obrigatório")
    private Long pacienteId;
    @NotNull(message = "O campo profissionalSaudeId é obrigatório")
    private Long profissionalSaudeId;
    @NotNull(message = "O campo dataHoraExame é obrigatório")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataHoraExame;
    @NotNull(message = "O tipo de exame é obrigatório")
    private TipoExame tipo;
    private String resultado;

}
