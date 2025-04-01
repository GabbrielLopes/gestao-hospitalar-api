package dev.gabbriellps.gestao.hospitalar.api.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Especialidade {

    CLINICO_GERAL("Clínico Geral"),
    CARDIOLOGISTA("Cardiologista"),
    ORTOPEDISTA("Ortopedista"),
    PEDIATRA("Pediatra"),
    NEUROLOGISTA("Neurologista"),
    OFTALMOLOGISTA("Oftalmologista");

    @Getter
    private final String descricao;

}