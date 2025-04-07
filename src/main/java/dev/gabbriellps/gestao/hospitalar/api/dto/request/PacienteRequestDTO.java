package dev.gabbriellps.gestao.hospitalar.api.dto.request;

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
public class PacienteRequestDTO {

    @Valid
    @NotNull(message = "Dados pessoa obrigatório")
    private PessoaRequestDTO pessoa;

}
