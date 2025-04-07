package dev.gabbriellps.gestao.hospitalar.api.service;

import dev.gabbriellps.gestao.hospitalar.api.dto.request.PessoaRequestDTO;
import dev.gabbriellps.gestao.hospitalar.api.handler.VidaPlusServiceException;
import dev.gabbriellps.gestao.hospitalar.api.model.Pessoa;
import dev.gabbriellps.gestao.hospitalar.api.service.interfaces.PessoaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class PessoaAbstractService {

    @Autowired
    protected PessoaService pessoaService;
    @Autowired
    protected ModelMapper mapper;


    protected Pessoa cadastrarPessoa(PessoaRequestDTO pessoaRequestDTO) throws VidaPlusServiceException {
        return pessoaService.cadastrarPessoa(pessoaRequestDTO);
    }

}
