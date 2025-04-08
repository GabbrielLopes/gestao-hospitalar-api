package dev.gabbriellps.gestao.hospitalar.api.repository;

import dev.gabbriellps.gestao.hospitalar.api.model.ProfissionalSaude;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfissionalSaudeRepository extends JpaRepository<ProfissionalSaude, Long> {

}
