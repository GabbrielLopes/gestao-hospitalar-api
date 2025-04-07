package dev.gabbriellps.gestao.hospitalar.api.service.interfaces;

import dev.gabbriellps.gestao.hospitalar.api.dto.request.PacienteRequestDTO;
import dev.gabbriellps.gestao.hospitalar.api.dto.response.PacienteResponseDTO;
import dev.gabbriellps.gestao.hospitalar.api.handler.VidaPlusServiceException;

import java.util.List;

public interface PacienteService {

    List<PacienteResponseDTO> consultarPacientes();


    PacienteResponseDTO consultarPacientePorId(Long id) throws VidaPlusServiceException;

    PacienteResponseDTO cadastrarPaciente(PacienteRequestDTO requestDTO) throws VidaPlusServiceException;

    void inativarPaciente(Long id) throws VidaPlusServiceException;

    void ativarPaciente(Long id) throws VidaPlusServiceException;

}
