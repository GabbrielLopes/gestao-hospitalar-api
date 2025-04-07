package dev.gabbriellps.gestao.hospitalar.api.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Sexo {

    M("Masculino"),
    F("Feminino"),
    O("Outro");


    @Getter
    private final String descricao;

}
