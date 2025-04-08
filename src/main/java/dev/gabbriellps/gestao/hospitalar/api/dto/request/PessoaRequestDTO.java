package dev.gabbriellps.gestao.hospitalar.api.dto.request;

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
public class PessoaRequestDTO {

    @NotBlank(message = "Nome obrigatório")
    private String nome;
    @NotNull(message = "Sexo obrigatório")
    private Sexo sexo;
    @NotNull(message = "Data nascimento obrigatório")
    private LocalDate dataNascimento;
    @NotBlank(message = "CPF obrigatório")
    private String cpf;
    @NotBlank(message = "CEP obrigatório")
    private String cep;
    @NotBlank(message = "Endereço obrigatório")
    private String endereco;
    private String complemento;
    @NotBlank(message = "Telefone obrigatório")
    private String telefone;
    @NotBlank(message = "Email obrigatório")
    private String email;

}
