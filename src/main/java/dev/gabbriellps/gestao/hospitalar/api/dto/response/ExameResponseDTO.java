package dev.gabbriellps.gestao.hospitalar.api.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.gabbriellps.gestao.hospitalar.api.enumeration.TipoExame;
import dev.gabbriellps.gestao.hospitalar.api.model.Exame;
import dev.gabbriellps.gestao.hospitalar.api.model.Paciente;
import dev.gabbriellps.gestao.hospitalar.api.model.ProfissionalSaude;
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
public class ExameResponseDTO {

    private Long id;
    private PacienteResponseDTO paciente;
    private ProfissionalResponseDTO profissionalSaude;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataHoraExame;
    private TipoExame tipo;
    private String resultado;

    public static ExameResponseDTO mapToExameResponseDTO(Exame exame) {
        return new ModelMapper().map(exame, ExameResponseDTO.class);
    }

}
