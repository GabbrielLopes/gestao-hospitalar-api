package dev.gabbriellps.gestao.hospitalar.api.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TipoConsulta {

    PRESENCIAL("Presencial"),
    TELEMEDICINA("Online"),
    URGENCIA("Urgência"),
    ROTINA("Rotina"),
    RETORNO("Retorno"),
    ESPECIALIZADA("Especializada"),
    PRE_OPERATORIA("Pré-operatória"),
    POS_OPERATORIA("Pós-operatória"),
    DOMICILIAR("Domiciliar"),
    OCUPACIONAL("Ocupacional");

    @Getter
    private final String descricao;

}