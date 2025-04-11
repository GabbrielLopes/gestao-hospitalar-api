package dev.gabbriellps.gestao.hospitalar.api.service.interfaces;

import dev.gabbriellps.gestao.hospitalar.api.dto.request.ExameRequestDTO;
import dev.gabbriellps.gestao.hospitalar.api.dto.response.ExameResponseDTO;
import dev.gabbriellps.gestao.hospitalar.api.handler.VidaPlusServiceException;

import java.util.List;

public interface ExameService {

    List<ExameResponseDTO> consultarExames();

    ExameResponseDTO consultarExamePorId(Long id) throws VidaPlusServiceException;

    ExameResponseDTO cadastrarExame(ExameRequestDTO requestDTO) throws VidaPlusServiceException;

    ExameResponseDTO editarExame(Long id, ExameRequestDTO requestDTO) throws VidaPlusServiceException;

    void excluirExame(Long id) throws VidaPlusServiceException;

}
