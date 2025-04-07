package dev.gabbriellps.gestao.hospitalar.api.dto.response;

import dev.gabbriellps.gestao.hospitalar.api.enumeration.Sexo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PessoaResponseDTO {

    private String nome;
    private Sexo sexo;
    private LocalDate dataNascimento;
    private String cpf;
    private String cep;
    private String endereco;
    private String complemento;
    private String telefone;
    private String email;

}
