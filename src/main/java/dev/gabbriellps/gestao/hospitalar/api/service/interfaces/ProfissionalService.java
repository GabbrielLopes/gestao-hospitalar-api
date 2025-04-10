package dev.gabbriellps.gestao.hospitalar.api.service.interfaces;

import dev.gabbriellps.gestao.hospitalar.api.dto.request.ProfissionalRequestDTO;
import dev.gabbriellps.gestao.hospitalar.api.dto.response.ProfissionalResponseDTO;
import dev.gabbriellps.gestao.hospitalar.api.handler.VidaPlusServiceException;
import dev.gabbriellps.gestao.hospitalar.api.model.ProfissionalSaude;

import java.util.List;

public interface ProfissionalService {
    List<ProfissionalResponseDTO> consultarProfissionais();

    ProfissionalResponseDTO consultarProfissionalSaudePorId(Long id) throws VidaPlusServiceException;

    ProfissionalResponseDTO cadastrarProfissionalSaude(ProfissionalRequestDTO requestDTO)
            throws VidaPlusServiceException;

    ProfissionalResponseDTO editarProfissionalSaude(Long id, ProfissionalRequestDTO requestDTO)
            throws VidaPlusServiceException;

    void inativarProfissionalSaude(Long id) throws VidaPlusServiceException;

    void ativarProfissionalSaude(Long id) throws VidaPlusServiceException;

    ProfissionalSaude findById(Long id) throws VidaPlusServiceException;

}
