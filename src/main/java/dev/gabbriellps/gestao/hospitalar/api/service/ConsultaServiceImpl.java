package dev.gabbriellps.gestao.hospitalar.api.service;

import dev.gabbriellps.gestao.hospitalar.api.dto.request.ConsultaRequestDTO;
import dev.gabbriellps.gestao.hospitalar.api.dto.response.ConsultaResponseDTO;
import dev.gabbriellps.gestao.hospitalar.api.enumeration.TipoAcao;
import dev.gabbriellps.gestao.hospitalar.api.handler.VidaPlusServiceException;
import dev.gabbriellps.gestao.hospitalar.api.model.Auditoria;
import dev.gabbriellps.gestao.hospitalar.api.model.Consulta;
import dev.gabbriellps.gestao.hospitalar.api.repository.ConsultaRepository;
import dev.gabbriellps.gestao.hospitalar.api.service.interfaces.AuditoriaService;
import dev.gabbriellps.gestao.hospitalar.api.service.interfaces.ConsultaService;
import dev.gabbriellps.gestao.hospitalar.api.service.interfaces.PacienteService;
import dev.gabbriellps.gestao.hospitalar.api.service.interfaces.ProfissionalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static dev.gabbriellps.gestao.hospitalar.api.model.Usuario.getUserAcao;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultaServiceImpl implements ConsultaService {


    private final ConsultaRepository repository;
    private final PacienteService pacienteService;
    private final ProfissionalService profissionalService;
    private final AuditoriaService auditoriaService;

    @Override
    @Transactional(readOnly = true)
    public List<ConsultaResponseDTO> listarConsultas() {
        return repository.findAll().stream().map(Consulta::mapToConsultaResponseDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ConsultaResponseDTO listarConsultaPorId(Long id) throws VidaPlusServiceException {
        return repository.findById(id)
                .map(Consulta::mapToConsultaResponseDTO)
                .orElseThrow(() -> new VidaPlusServiceException("Consulta não encontrada", HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public ConsultaResponseDTO cadastrarConsulta(ConsultaRequestDTO consultaRequestDTO)
            throws VidaPlusServiceException {

        Consulta consulta = Consulta.builder()
                .paciente(pacienteService.findById(consultaRequestDTO.getPacienteId()))
                .profissionalSaude(profissionalService.findById(consultaRequestDTO.getProfissionalSaudeId()))
                .dataHoraConsulta(consultaRequestDTO.getDataHoraConsulta())
                .tipo(consultaRequestDTO.getTipo())
                .prontuario(consultaRequestDTO.getProntuario())
                .receitaDigital(consultaRequestDTO.getReceitaDigital())
                .teleconsulta(consultaRequestDTO.getTeleconsulta())
                .build();

        try {
            repository.saveAndFlush(consulta);
            auditoriaService.saveAuditoria(Auditoria.record(TipoAcao.CADASTRO, getUserAcao(),
                    "Usuario cadastrou uma consulta - id consulta: " + consulta.getId()));
        } catch (Exception e) {
            log.error("Erro ao cadastrar consulta - ", e);
            throw new VidaPlusServiceException("Erro ao cadastrar consulta", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return consulta.toConsultaResponseDTO();
    }

    @Override
    @Transactional
    public ConsultaResponseDTO atualizarConsulta(Long id, ConsultaRequestDTO consultaRequestDTO)
            throws VidaPlusServiceException {
        Consulta consulta = findConsultaOrElseThrow(id);

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(consultaRequestDTO, consulta);

        consulta.setPaciente(pacienteService.findById(consultaRequestDTO.getPacienteId()));
        consulta.setProfissionalSaude(profissionalService.findById(consultaRequestDTO.getProfissionalSaudeId()));

        try {
            repository.saveAndFlush(consulta);
            auditoriaService.saveAuditoria(Auditoria.record(TipoAcao.EDICAO, getUserAcao(),
                    "Usuario editou uma consulta - id consulta: " + consulta.getId()));
        } catch (DataAccessException | HibernateException e) {
            log.error("Erro ao atualizar consulta - ", e);
            throw new VidaPlusServiceException("Erro ao atualizar consulta", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return consulta.toConsultaResponseDTO();
    }

    private Consulta findConsultaOrElseThrow(Long id) throws VidaPlusServiceException {
        return repository.findById(id)
                .orElseThrow(() -> new VidaPlusServiceException("Consulta não encontrada", HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public void excluirConsulta(Long id) throws VidaPlusServiceException {
        Consulta consulta = findConsultaOrElseThrow(id);
        try {
            repository.delete(consulta);
            auditoriaService.saveAuditoria(Auditoria.record(TipoAcao.EXCLUSAO, getUserAcao(),
                    "Usuario excluiu uma consulta - id consulta: " + consulta.getId()));
        } catch (DataAccessException | HibernateException e) {
            log.error("Erro ao excluir consulta - ", e);
            throw new VidaPlusServiceException("Erro ao excluir consulta", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConsultaResponseDTO> listarConsultasPorPeriodo(LocalDate dataInicio, LocalDate dataFim)
            throws VidaPlusServiceException {
        return Optional.of(repository.buscaConsultasPorPeriodo(dataInicio, dataFim)
                        .stream()
                        .map(Consulta::mapToConsultaResponseDTO)
                        .toList())
                .orElseThrow(() -> new VidaPlusServiceException("Consulta não encontrada com o periodo informado",
                        HttpStatus.NOT_FOUND));
    }

}
