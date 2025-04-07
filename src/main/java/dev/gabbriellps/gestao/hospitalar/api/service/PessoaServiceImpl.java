package dev.gabbriellps.gestao.hospitalar.api.service;

import dev.gabbriellps.gestao.hospitalar.api.dto.request.PessoaRequestDTO;
import dev.gabbriellps.gestao.hospitalar.api.handler.VidaPlusServiceException;
import dev.gabbriellps.gestao.hospitalar.api.model.Pessoa;
import dev.gabbriellps.gestao.hospitalar.api.repository.PessoaRepository;
import dev.gabbriellps.gestao.hospitalar.api.service.interfaces.PessoaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PessoaServiceImpl implements PessoaService {


    private final PessoaRepository repository;
    private final ModelMapper mapper;


    @Override
    public Pessoa cadastrarPessoa(PessoaRequestDTO pessoaRequestDTO) throws VidaPlusServiceException {
        Pessoa pessoa = mapper.map(pessoaRequestDTO, Pessoa.class);
        try {
            return repository.saveAndFlush(pessoa);
        } catch (DataAccessException | HibernateException e) {
            log.error("Erro ao cadastrar pessoa - ", e);
            throw new VidaPlusServiceException("Erro ao cadastrar dados pessoa", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
