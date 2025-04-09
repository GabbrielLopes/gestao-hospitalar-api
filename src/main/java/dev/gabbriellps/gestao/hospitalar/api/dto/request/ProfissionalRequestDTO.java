package dev.gabbriellps.gestao.hospitalar.api.dto.request;

import dev.gabbriellps.gestao.hospitalar.api.enumeration.Especialidade;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfissionalRequestDTO {

    @Valid
    @NotNull(message = "Dados pessoa obrigatório")
    private PessoaRequestDTO pessoa;

    @NotNull(message = "Especialidade obrigatória")
    private Especialidade especialidade;

    @NotNull(message = "Registro profissional obrigatório")
    private String registroProfissional;

}
