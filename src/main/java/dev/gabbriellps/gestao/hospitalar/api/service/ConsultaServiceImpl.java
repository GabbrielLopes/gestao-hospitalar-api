package dev.gabbriellps.gestao.hospitalar.api.service;

import dev.gabbriellps.gestao.hospitalar.api.dto.response.ConsultaDTO;
import dev.gabbriellps.gestao.hospitalar.api.service.interfaces.ConsultaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ConsultaServiceImpl implements ConsultaService {



    @Override
    public List<ConsultaDTO> listarConsultas() {

        return null;
    }

    @Override
    public ConsultaDTO atualizarConsulta(Long id, ConsultaDTO consultaDTO) {
        return null;
    }

    @Override
    public void excluirConsulta(Long id) {

    }
}
