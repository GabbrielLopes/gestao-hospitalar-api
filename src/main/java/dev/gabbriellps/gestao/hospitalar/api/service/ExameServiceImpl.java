package dev.gabbriellps.gestao.hospitalar.api.service;

import dev.gabbriellps.gestao.hospitalar.api.dto.request.ExameRequestDTO;
import dev.gabbriellps.gestao.hospitalar.api.dto.response.ExameResponseDTO;
import dev.gabbriellps.gestao.hospitalar.api.handler.VidaPlusServiceException;
import dev.gabbriellps.gestao.hospitalar.api.model.Exame;
import dev.gabbriellps.gestao.hospitalar.api.repository.ExameRepository;
import dev.gabbriellps.gestao.hospitalar.api.service.interfaces.ExameService;
import dev.gabbriellps.gestao.hospitalar.api.service.interfaces.PacienteService;
import dev.gabbriellps.gestao.hospitalar.api.service.interfaces.ProfissionalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExameServiceImpl implements ExameService {

    private final ExameRepository repository;
    private final PacienteService pacienteService;
    private final ProfissionalService profissionalService;


    @Override
    @Transactional(readOnly = true)
    public List<ExameResponseDTO> consultarExames() {
        return repository.findAll().stream()
                .map(ExameResponseDTO::mapToExameResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ExameResponseDTO consultarExamePorId(Long id) throws VidaPlusServiceException {
        return repository.findById(id)
                .map(ExameResponseDTO::mapToExameResponseDTO)
                .orElseThrow(() -> new VidaPlusServiceException("Exame não encontrado", HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public ExameResponseDTO cadastrarExame(ExameRequestDTO requestDTO) throws VidaPlusServiceException {

        Exame exame = Exame.builder()
                .paciente(pacienteService.findById(requestDTO.getPacienteId()))
                .profissionalSaude(profissionalService.findById(requestDTO.getProfissionalSaudeId()))
                .dataHoraExame(requestDTO.getDataHoraExame())
                .tipo(requestDTO.getTipo())
                .resultado(requestDTO.getResultado())
                .build();

        try {
            return repository.saveAndFlush(exame).toExameResponseDTO();
        } catch (DataAccessException | HibernateException e) {
            log.error("Erro ao cadastrar exame: {}", e.getMessage());
            throw new VidaPlusServiceException("Erro ao cadastrar exame", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public ExameResponseDTO editarExame(Long id, ExameRequestDTO requestDTO) throws VidaPlusServiceException {

        Exame exame = repository.findById(id)
                .orElseThrow(() -> new VidaPlusServiceException("Exame não encontrado", HttpStatus.NOT_FOUND));

        exame.atualizaDadosExame(requestDTO);
        exame.setPaciente(pacienteService.findById(requestDTO.getPacienteId()));
        exame.setProfissionalSaude(profissionalService.findById(requestDTO.getProfissionalSaudeId()));

        try {
            return repository.saveAndFlush(exame).toExameResponseDTO();
        } catch (DataAccessException | HibernateException e) {
            log.error("Erro ao editar exame: {}", e.getMessage());
            throw new VidaPlusServiceException("Erro ao editar exame", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public void excluirExame(Long id) throws VidaPlusServiceException {
        Exame exame = repository.findById(id)
                .orElseThrow(() -> new VidaPlusServiceException("Exame não encontrado", HttpStatus.NOT_FOUND));

        try {
            repository.delete(exame);
        } catch (DataAccessException | HibernateException e) {
            log.error("Erro ao excluir exame - ", e);
            throw new VidaPlusServiceException("Erro ao excluir exame", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
