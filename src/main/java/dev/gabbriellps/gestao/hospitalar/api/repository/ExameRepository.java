package dev.gabbriellps.gestao.hospitalar.api.repository;

import dev.gabbriellps.gestao.hospitalar.api.model.Exame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExameRepository extends JpaRepository<Exame, Long> {


}
