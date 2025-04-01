package dev.gabbriellps.gestao.hospitalar.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "PESSOA")
public class Pessoa {

    @Id
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NOME", nullable = false, length = 150)
    private String nome;

    @Column(name = "SEXO", nullable = false, length = 1)
    private String sexo;

    @Column(name = "DT_NASCIMENTO", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "CPF", nullable = false, length = 11)
    private String cpf;

    @Column(name = "CEP", nullable = false, length = 11)
    private String cep;

    @Column(name = "ENDERECO", nullable = false)
    private String endereco;

    @Column(name = "COMPLEMENTO", length = 100)
    private String complemento;

    @Column(name = "TELEFONE", nullable = false, length = 11)
    private String telefone;

    @Column(name = "EMAIL", nullable = false)
    private String email;


}
