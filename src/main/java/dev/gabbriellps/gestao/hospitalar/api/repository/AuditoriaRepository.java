package dev.gabbriellps.gestao.hospitalar.api.repository;

import dev.gabbriellps.gestao.hospitalar.api.model.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditoriaRepository extends JpaRepository<Auditoria, Long> {

}
