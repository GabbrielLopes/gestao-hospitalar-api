package dev.gabbriellps.gestao.hospitalar.api.service.interfaces;

import dev.gabbriellps.gestao.hospitalar.api.dto.request.PacienteRequestDTO;
import dev.gabbriellps.gestao.hospitalar.api.dto.response.PacienteResponseDTO;
import dev.gabbriellps.gestao.hospitalar.api.handler.VidaPlusServiceException;
import dev.gabbriellps.gestao.hospitalar.api.model.Paciente;

import java.util.List;

public interface PacienteService {

    List<PacienteResponseDTO> consultarPacientes();

    PacienteResponseDTO consultarPacientePorId(Long id) throws VidaPlusServiceException;

    PacienteResponseDTO cadastrarPaciente(PacienteRequestDTO requestDTO) throws VidaPlusServiceException;

    void inativarPaciente(Long id) throws VidaPlusServiceException;

    void ativarPaciente(Long id) throws VidaPlusServiceException;

    PacienteResponseDTO editarPaciente(Long id, PacienteRequestDTO requestDTO) throws VidaPlusServiceException;

    Paciente findById(Long id) throws VidaPlusServiceException;
}
