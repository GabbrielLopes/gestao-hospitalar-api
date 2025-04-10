package dev.gabbriellps.gestao.hospitalar.api.repository;

import dev.gabbriellps.gestao.hospitalar.api.enumeration.Especialidade;
import dev.gabbriellps.gestao.hospitalar.api.model.ProfissionalSaude;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ProfissionalSaudeRepository extends JpaRepository<ProfissionalSaude, Long> {


    @Query("SELECT p " +
            "FROM ProfissionalSaude p " +
            "WHERE (:filtro IS NULL OR (p.pessoa.nome LIKE %:filtro% OR p.pessoa.cpf LIKE %:filtro% OR p.pessoa.email LIKE %:filtro%)) " +
            "AND (:especialidade IS NULL OR p.especialidade = :especialidade)")
    Collection<ProfissionalSaude> buscaProfissionaisPorFiltro(@Param("filtro") String filtro,
                                                              @Param("especialidade") Especialidade especialidade);

}
