package dev.gabbriellps.gestao.hospitalar.api.service;

import dev.gabbriellps.gestao.hospitalar.api.configuration.security.SecurityHelper;
import dev.gabbriellps.gestao.hospitalar.api.dto.request.PacienteRequestDTO;
import dev.gabbriellps.gestao.hospitalar.api.dto.response.PacienteResponseDTO;
import dev.gabbriellps.gestao.hospitalar.api.enumeration.TipoAcao;
import dev.gabbriellps.gestao.hospitalar.api.handler.VidaPlusServiceException;
import dev.gabbriellps.gestao.hospitalar.api.model.Auditoria;
import dev.gabbriellps.gestao.hospitalar.api.model.Paciente;
import dev.gabbriellps.gestao.hospitalar.api.model.Pessoa;
import dev.gabbriellps.gestao.hospitalar.api.repository.PacienteRepository;
import dev.gabbriellps.gestao.hospitalar.api.service.interfaces.AuditoriaService;
import dev.gabbriellps.gestao.hospitalar.api.service.interfaces.PacienteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class PacienteServiceImpl extends PessoaAbstractService implements PacienteService {

    private final PacienteRepository repository;
    private final AuditoriaService auditoriaService;
    private final SecurityHelper securityHelper;

    @Override
    @Transactional(readOnly = true)
    public List<PacienteResponseDTO> consultarPacientes() {
        return repository.findAll().stream()
                .map(Paciente::mapToPacienteResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PacienteResponseDTO consultarPacientePorId(Long id) throws VidaPlusServiceException {
        return repository.findById(id).map(Paciente::mapToPacienteResponseDTO)
                .orElseThrow(() -> new VidaPlusServiceException("Paciente não encontrado", HttpStatus.NOT_FOUND));
    }

    @Override
    public PacienteResponseDTO cadastrarPaciente(PacienteRequestDTO requestDTO) throws VidaPlusServiceException {

        Pessoa pessoa = cadastrarPessoa(requestDTO.getPessoa());

        try {
            Paciente paciente = Paciente.builder()
                    .pessoa(pessoa)
                    .build();

            repository.saveAndFlush(paciente);
            auditoriaService.saveAuditoria(Auditoria.record(TipoAcao.CADASTRO, securityHelper.getUserLogado(),
                    "Usuario cadastrou um paciente - id paciente: " + paciente.getId()));

            return paciente.toPacienteResponseDTO();
        } catch (DataAccessException | HibernateException e) {
            log.error("Erro ao cadastrar paciente - ", e);
            throw new VidaPlusServiceException("Erro ao cadastrar paciente", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public PacienteResponseDTO editarPaciente(Long id, PacienteRequestDTO requestDTO) throws VidaPlusServiceException {
        Paciente paciente = retornaPacientePorId(id);

        try {
            paciente.atualizaDadosPessoa(requestDTO);

            PacienteResponseDTO response = repository.saveAndFlush(paciente).toPacienteResponseDTO();
            auditoriaService.saveAuditoria(Auditoria.record(TipoAcao.EDICAO, securityHelper.getUserLogado(),
                    "Usuario editou um paciente - id paciente: " + paciente.getId()));
            return response;
        } catch (DataAccessException | HibernateException e) {
            log.error("Erro ao editar paciente - ", e);
            throw new VidaPlusServiceException("Erro ao editar paciente", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Paciente findById(Long id) throws VidaPlusServiceException {
        return repository.findById(id)
                .orElseThrow(() -> new VidaPlusServiceException("Paciente não encontrado com id informado",
                        HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PacienteResponseDTO> consultarPacienteComFiltro(String filtro) throws VidaPlusServiceException {
        return Optional.of(repository.buscaPorFiltro(filtro)
                        .stream()
                        .map(Paciente::mapToPacienteResponseDTO)
                        .toList())
                .orElseThrow(() -> new VidaPlusServiceException("Paciente não encontrado com o parametro informado",
                        HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public void inativarPaciente(Long id) throws VidaPlusServiceException {
        Paciente paciente = retornaPacientePorId(id);
        paciente.inativar();

        try {
            repository.saveAndFlush(paciente);
            auditoriaService.saveAuditoria(Auditoria.record(TipoAcao.INATIVACAO, securityHelper.getUserLogado(),
                    "Usuario inativou um paciente - id paciente: " + paciente.getId()));
        } catch (DataAccessException | HibernateException e) {
            log.error("Erro ao inativar paciente - ", e);
            throw new VidaPlusServiceException("Erro ao excluir paciente", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Paciente retornaPacientePorId(Long id) throws VidaPlusServiceException {
        return repository.findById(id)
                .orElseThrow(() -> new VidaPlusServiceException("Paciente não encontrado com id informado",
                        HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public void ativarPaciente(Long id) throws VidaPlusServiceException {
        Paciente paciente = retornaPacientePorId(id);
        paciente.ativar();

        try {
            repository.saveAndFlush(paciente);
            auditoriaService.saveAuditoria(Auditoria.record(TipoAcao.ATIVACAO, securityHelper.getUserLogado(),
                    "Usuario ativou um paciente - id paciente: " + paciente.getId()));
        } catch (DataAccessException | HibernateException e) {
            log.error("Erro ao ativar paciente - ", e);
            throw new VidaPlusServiceException("Erro ao ativar paciente", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
