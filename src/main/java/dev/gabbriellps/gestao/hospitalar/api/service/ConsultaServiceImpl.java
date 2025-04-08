package dev.gabbriellps.gestao.hospitalar.api.service;

import dev.gabbriellps.gestao.hospitalar.api.dto.response.ConsultaResponseDTO;
import dev.gabbriellps.gestao.hospitalar.api.service.interfaces.ConsultaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ConsultaServiceImpl implements ConsultaService {



    @Override
    public List<ConsultaResponseDTO> listarConsultas() {

        return null;
    }

    @Override
    public ConsultaResponseDTO atualizarConsulta(Long id, ConsultaResponseDTO consultaResponseDTO) {
        return null;
    }

    @Override
    public void excluirConsulta(Long id) {

    }
}
