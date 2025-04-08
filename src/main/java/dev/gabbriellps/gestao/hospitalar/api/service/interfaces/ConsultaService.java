package dev.gabbriellps.gestao.hospitalar.api.service.interfaces;

import dev.gabbriellps.gestao.hospitalar.api.dto.response.ConsultaDTO;

import java.util.List;

public interface ConsultaService {


    List<ConsultaDTO> listarConsultas();

    ConsultaDTO atualizarConsulta(Long id, ConsultaDTO consultaDTO);

    void excluirConsulta(Long id);
}
