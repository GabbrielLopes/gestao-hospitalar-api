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

    @NotBlank(message = "Nome do paciente obrigatório")
    private String nome;
    @NotNull(message = "Sexo do paciente obrigatório")
    private Sexo sexo;
    @NotNull(message = "Data nascimento do paciente obrigatório")
    private LocalDate dataNascimento;
    @NotBlank(message = "CPF do paciente obrigatório")
    private String cpf;
    @NotBlank(message = "CEP do paciente obrigatório")
    private String cep;
    @NotBlank(message = "Endereço do paciente obrigatório")
    private String endereco;
    private String complemento;
    @NotBlank(message = "Telefone do paciente obrigatório")
    private String telefone;
    @NotBlank(message = "Email do paciente obrigatório")
    private String email;

}
