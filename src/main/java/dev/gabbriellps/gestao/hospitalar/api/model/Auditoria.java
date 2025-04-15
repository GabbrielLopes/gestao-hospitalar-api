package dev.gabbriellps.gestao.hospitalar.api.model;

import dev.gabbriellps.gestao.hospitalar.api.enumeration.TipoAcao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@SequenceGenerator(name = "AUDITORIA_SEQ", sequenceName = "AUDITORIA_SEQ", allocationSize = 1)
@Table(name = "AUDITORIA")
public class Auditoria {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUDITORIA_SEQ")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID", nullable = false)
    private Usuario usuario;

    @Column(name = "ACAO", nullable = false)
    private String acao;

    @Column(name = "TIPO_ACAO", length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoAcao tipoAcao;

    @Column(name = "DTHR_OPERACAO", nullable = false)
    private LocalDateTime dataHoraOperacao;

    @PrePersist
    protected void onCreate() {
        dataHoraOperacao = LocalDateTime.now();
    }


    public static Auditoria record(TipoAcao tipoAcao, Usuario usuario, String acao) {
        return Auditoria.builder()
                .tipoAcao(tipoAcao)
                .usuario(usuario)
                .acao(acao)
                .build();
    }

}
