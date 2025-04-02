package dev.gabbriellps.gestao.hospitalar.api.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TipoExame {

    SANGUE("Sangue"),
    URINA("Urina"),
    IMAGEM("Imagem"),
    CARDIACO("Cardíaco"),
    ENDOSCOPIA("Endoscopia"),
    COLONOSCOPIA("Colonoscopia"),
    ULTRASSOM("Ultrassom"),
    TESTE_ESFORCO("Teste de Esforço"),
    BIOPSIA("Biópsia"),
    GENETICO("Genético");

    @Getter
    private final String descricao;

}
