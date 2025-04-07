package dev.gabbriellps.gestao.hospitalar.api.service.interfaces;

import dev.gabbriellps.gestao.hospitalar.api.dto.request.PessoaRequestDTO;
import dev.gabbriellps.gestao.hospitalar.api.handler.VidaPlusServiceException;
import dev.gabbriellps.gestao.hospitalar.api.model.Pessoa;

public interface PessoaService {
    Pessoa cadastrarPessoa(PessoaRequestDTO pessoaRequestDTO) throws VidaPlusServiceException;

}
