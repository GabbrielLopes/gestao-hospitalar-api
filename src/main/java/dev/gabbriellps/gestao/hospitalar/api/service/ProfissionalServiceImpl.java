package dev.gabbriellps.gestao.hospitalar.api.service;

import dev.gabbriellps.gestao.hospitalar.api.dto.request.ProfissionalRequestDTO;
import dev.gabbriellps.gestao.hospitalar.api.dto.response.ProfissionalResponseDTO;
import dev.gabbriellps.gestao.hospitalar.api.enumeration.Especialidade;
import dev.gabbriellps.gestao.hospitalar.api.enumeration.TipoAcao;
import dev.gabbriellps.gestao.hospitalar.api.handler.VidaPlusServiceException;
import dev.gabbriellps.gestao.hospitalar.api.model.Auditoria;
import dev.gabbriellps.gestao.hospitalar.api.model.Pessoa;
import dev.gabbriellps.gestao.hospitalar.api.model.ProfissionalSaude;
import dev.gabbriellps.gestao.hospitalar.api.repository.ProfissionalSaudeRepository;
import dev.gabbriellps.gestao.hospitalar.api.service.interfaces.AuditoriaService;
import dev.gabbriellps.gestao.hospitalar.api.service.interfaces.ProfissionalService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static dev.gabbriellps.gestao.hospitalar.api.model.Usuario.getUserAcao;

@Slf4j
@Service
@AllArgsConstructor
public class ProfissionalServiceImpl extends PessoaAbstractService implements ProfissionalService {

    private final ProfissionalSaudeRepository repository;
    private final AuditoriaService auditoriaService;

    @Override
    @Transactional(readOnly = true)
    public List<ProfissionalResponseDTO> consultarProfissionais() {
        return repository.findAll().stream()
                .map(ProfissionalSaude::mapToProfissionalSaudeResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProfissionalResponseDTO consultarProfissionalSaudePorId(Long id) throws VidaPlusServiceException {
        return repository.findById(id).map(ProfissionalSaude::mapToProfissionalSaudeResponseDTO)
                .orElseThrow(() -> new VidaPlusServiceException("ProfissionalSaude não encontrado", HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public ProfissionalResponseDTO cadastrarProfissionalSaude(ProfissionalRequestDTO requestDTO)
            throws VidaPlusServiceException {

        Pessoa pessoa = cadastrarPessoa(requestDTO.getPessoa());

        try {
            ProfissionalSaude profissional = ProfissionalSaude.builder()
                    .pessoa(pessoa)
                    .especialidade(requestDTO.getEspecialidade())
                    .registroProfissional(requestDTO.getRegistroProfissional())
                    .build();

            repository.saveAndFlush(profissional);
            auditoriaService.saveAuditoria(Auditoria.record(TipoAcao.CADASTRO, getUserAcao(),
                    "Usuario cadastrou um profissional - id profissional: " + profissional.getId()));

            return profissional.toProfissionalSaudeResponseDTO();
        } catch (DataAccessException | HibernateException e) {
            log.error("Erro ao cadastrar profissional - ", e);
            throw new VidaPlusServiceException("Erro ao cadastrar profissional", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public ProfissionalResponseDTO editarProfissionalSaude(Long id, ProfissionalRequestDTO requestDTO)
            throws VidaPlusServiceException {
        ProfissionalSaude profissionalSaude = retornaProfissionalPorId(id);

        try {
            profissionalSaude.atualizaDadosPessoa(requestDTO);

            ProfissionalResponseDTO response = repository.saveAndFlush(profissionalSaude).toProfissionalSaudeResponseDTO();
            auditoriaService.saveAuditoria(Auditoria.record(TipoAcao.EDICAO, getUserAcao(),
                    "Usuario editou um profissional - id profissional: " + profissionalSaude.getId()));

            return response;
        } catch (DataAccessException | HibernateException e){
            log.error("Erro ao editar profissional - ", e);
            throw new VidaPlusServiceException("Erro ao editar profissional", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public void inativarProfissionalSaude(Long id) throws VidaPlusServiceException {
        ProfissionalSaude profissionalSaude = retornaProfissionalPorId(id);
        profissionalSaude.inativar();

        try {
            repository.saveAndFlush(profissionalSaude);
            auditoriaService.saveAuditoria(Auditoria.record(TipoAcao.INATIVACAO, getUserAcao(),
                    "Usuario inativou um profissional - id profissional: " + profissionalSaude.getId()));
        } catch (DataAccessException | HibernateException e) {
            log.error("Erro ao inativar profissional - ", e);
            throw new VidaPlusServiceException("Erro ao excluir profissional", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ProfissionalSaude retornaProfissionalPorId(Long id) throws VidaPlusServiceException {
        return repository.findById(id)
                .orElseThrow(() -> new VidaPlusServiceException("ProfissionalSaude não encontrado com id informado",
                        HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public void ativarProfissionalSaude(Long id) throws VidaPlusServiceException {
        ProfissionalSaude profissional = retornaProfissionalPorId(id);

        profissional.ativar();

        try {
            repository.saveAndFlush(profissional);
            auditoriaService.saveAuditoria(Auditoria.record(TipoAcao.ATIVACAO, getUserAcao(),
                    "Usuario ativou um profissional - id profissional: " + profissional.getId()));
        } catch (DataAccessException | HibernateException e) {
            log.error("Erro ao ativar profissional - ", e);
            throw new VidaPlusServiceException("Erro ao ativar profissional", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ProfissionalSaude findById(Long id) throws VidaPlusServiceException {
        return repository.findById(id)
                .orElseThrow(() -> new VidaPlusServiceException("Profissional não encontrado com id informado",
                        HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public List<ProfissionalResponseDTO> consultarProfissionalPorFiltro(String filtro, Especialidade especialidade) {
        return repository.buscaProfissionaisPorFiltro(filtro, especialidade).stream()
                .map(ProfissionalSaude::mapToProfissionalSaudeResponseDTO)
                .toList();
    }

}
