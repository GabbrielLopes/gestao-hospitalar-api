package dev.gabbriellps.gestao.hospitalar.api.service;

import dev.gabbriellps.gestao.hospitalar.api.model.Auditoria;
import dev.gabbriellps.gestao.hospitalar.api.repository.AuditoriaRepository;
import dev.gabbriellps.gestao.hospitalar.api.service.interfaces.AuditoriaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditoriaServiceImpl implements AuditoriaService {


    private final AuditoriaRepository repository;

    @Override
    @Transactional
    public void saveAuditoria(Auditoria auditoria) {
        log.info("Salvando registro auditoria: {}", auditoria);
        try {
            repository.saveAndFlush(auditoria);
        } catch (DataAccessException | HibernateException e) {
            log.error("Error ao salva registro auditoria - ", e);
        }
    }

}
