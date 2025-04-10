package dev.gabbriellps.gestao.hospitalar.api.repository;

import dev.gabbriellps.gestao.hospitalar.api.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {


    @Query("SELECT p " +
            "FROM Paciente p " +
            "WHERE p.ativo = true " +
            "AND (p.pessoa.nome LIKE %:filtro% OR p.pessoa.cpf LIKE %:filtro% OR p.pessoa.email LIKE %:filtro%)")
    Collection<Paciente> buscaPorFiltro(@Param("filtro") String filtro);

}
