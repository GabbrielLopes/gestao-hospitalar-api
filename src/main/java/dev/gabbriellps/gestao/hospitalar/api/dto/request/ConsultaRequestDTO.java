package dev.gabbriellps.gestao.hospitalar.api.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.gabbriellps.gestao.hospitalar.api.dto.response.PacienteResponseDTO;
import dev.gabbriellps.gestao.hospitalar.api.dto.response.ProfissionalResponseDTO;
import dev.gabbriellps.gestao.hospitalar.api.enumeration.TipoConsulta;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NegativeOrZero;
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
public class ConsultaRequestDTO {

    @NotNull(message = "O campo pacienteId é obrigatório")
    private Long pacienteId;
    @NotNull(message = "O campo profissionalSaudeId é obrigatório")
    private Long profissionalSaudeId;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataHoraConsulta;
    private TipoConsulta tipo;
    private String prontuario;
    private String receitaDigital;
    private Boolean teleconsulta;

}
