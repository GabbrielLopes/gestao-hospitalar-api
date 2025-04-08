package dev.gabbriellps.gestao.hospitalar.api.service.interfaces;

import dev.gabbriellps.gestao.hospitalar.api.dto.response.ConsultaResponseDTO;

import java.util.List;

public interface ConsultaService {


    List<ConsultaResponseDTO> listarConsultas();

    ConsultaResponseDTO atualizarConsulta(Long id, ConsultaResponseDTO consultaResponseDTO);

    void excluirConsulta(Long id);
}
