package dev.gabbriellps.gestao.hospitalar.api.dto.response;

import dev.gabbriellps.gestao.hospitalar.api.enumeration.TipoConsulta;
import dev.gabbriellps.gestao.hospitalar.api.model.Paciente;
import dev.gabbriellps.gestao.hospitalar.api.model.ProfissionalSaude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsultaDTO {

    private Long id;
    private Paciente paciente;
    private ProfissionalSaude profissionalSaude;
    private LocalDateTime dataHoraConsulta;
    private TipoConsulta tipo;
    private String prontuario;
    private String receitaDigital;
    private Boolean teleconsulta;

    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;


}
