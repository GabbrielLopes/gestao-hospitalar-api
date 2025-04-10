package dev.gabbriellps.gestao.hospitalar.api.repository;

import dev.gabbriellps.gestao.hospitalar.api.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {


    @Query("SELECT c " +
            "FROM Consulta c " +
            "WHERE DATE(c.dataHoraConsulta) BETWEEN :dataInicio AND :dataFim ")
    Collection<Consulta> buscaConsultasPorPeriodo(@Param("dataInicio") LocalDate dataInicio,
                                                  @Param("dataFim") LocalDate dataFim);

}
