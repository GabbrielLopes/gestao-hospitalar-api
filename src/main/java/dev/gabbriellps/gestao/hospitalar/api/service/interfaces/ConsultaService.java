package dev.gabbriellps.gestao.hospitalar.api.service.interfaces;

import dev.gabbriellps.gestao.hospitalar.api.dto.request.ConsultaRequestDTO;
import dev.gabbriellps.gestao.hospitalar.api.dto.response.ConsultaResponseDTO;
import dev.gabbriellps.gestao.hospitalar.api.handler.VidaPlusServiceException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ConsultaService {


    List<ConsultaResponseDTO> listarConsultas();
    ConsultaResponseDTO listarConsultaPorId(Long id) throws VidaPlusServiceException;

    ConsultaResponseDTO cadastrarConsulta(ConsultaRequestDTO consultaRequestDTO)
            throws VidaPlusServiceException;

    ConsultaResponseDTO atualizarConsulta(Long id, ConsultaRequestDTO consultaResponseDTO)
            throws VidaPlusServiceException;

    void excluirConsulta(Long id) throws VidaPlusServiceException;


}
