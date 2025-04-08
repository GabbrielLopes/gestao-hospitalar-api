package dev.gabbriellps.gestao.hospitalar.api.service;

import dev.gabbriellps.gestao.hospitalar.api.dto.request.ProfissionalSaudeRequestDTO;
import dev.gabbriellps.gestao.hospitalar.api.dto.response.ProfissionalSaudeResponseDTO;
import dev.gabbriellps.gestao.hospitalar.api.handler.VidaPlusServiceException;
import dev.gabbriellps.gestao.hospitalar.api.model.Pessoa;
import dev.gabbriellps.gestao.hospitalar.api.model.ProfissionalSaude;
import dev.gabbriellps.gestao.hospitalar.api.repository.ProfissionalSaudeRepository;
import dev.gabbriellps.gestao.hospitalar.api.service.interfaces.ProfissionalSaudeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ProfissionalSaudeServiceImpl extends PessoaAbstractService implements ProfissionalSaudeService {

    private final ProfissionalSaudeRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<ProfissionalSaudeResponseDTO> consultarProfissionais() {
        return repository.findAll().stream()
                .map(ProfissionalSaude::mapToProfissionalSaudeResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProfissionalSaudeResponseDTO consultarProfissionalSaudePorId(Long id) throws VidaPlusServiceException {
        return repository.findById(id).map(ProfissionalSaude::mapToProfissionalSaudeResponseDTO)
                .orElseThrow(() -> new VidaPlusServiceException("ProfissionalSaude não encontrado", HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public ProfissionalSaudeResponseDTO cadastrarProfissionalSaude(ProfissionalSaudeRequestDTO requestDTO)
            throws VidaPlusServiceException {

        Pessoa pessoa = cadastrarPessoa(requestDTO.getPessoa());

        try {
            ProfissionalSaude profissional = ProfissionalSaude.builder()
                    .pessoa(pessoa)
                    .especialidade(requestDTO.getEspecialidade())
                    .registroProfissional(requestDTO.getRegistroProfissional())
                    .build();

            repository.saveAndFlush(profissional);

            return profissional.toProfissionalSaudeResponseDTO();
        } catch (DataAccessException | HibernateException e) {
            log.error("Erro ao cadastrar profissional - ", e);
            throw new VidaPlusServiceException("Erro ao cadastrar profissional", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public ProfissionalSaudeResponseDTO editarProfissionalSaude(Long id, ProfissionalSaudeRequestDTO requestDTO)
            throws VidaPlusServiceException {
        ProfissionalSaude profissionalSaude = retornaProfissionalPorId(id);

        try {
            profissionalSaude.atualizaDadosPessoa(requestDTO);

            return repository.saveAndFlush(profissionalSaude).toProfissionalSaudeResponseDTO();
        } catch (DataAccessException | HibernateException e){
            log.error("Erro ao editar profissional - ", e);
            throw new VidaPlusServiceException("Erro ao editar profissional", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public void inativarProfissionalSaude(Long id) throws VidaPlusServiceException {
        ProfissionalSaude profissionalSaude = retornaProfissionalPorId(id);
        profissionalSaude.inativar();

        try {
            repository.saveAndFlush(profissionalSaude);
        } catch (DataAccessException | HibernateException e) {
            log.error("Erro ao inativar profissional - ", e);
            throw new VidaPlusServiceException("Erro ao excluir profissional", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ProfissionalSaude retornaProfissionalPorId(Long id) throws VidaPlusServiceException {
        return repository.findById(id)
                .orElseThrow(() -> new VidaPlusServiceException("ProfissionalSaude não encontrado com id informado",
                        HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public void ativarProfissionalSaude(Long id) throws VidaPlusServiceException {
        ProfissionalSaude profissional = retornaProfissionalPorId(id);

        profissional.ativar();

        try {
            repository.saveAndFlush(profissional);
        } catch (DataAccessException | HibernateException e) {
            log.error("Erro ao ativar profissional - ", e);
            throw new VidaPlusServiceException("Erro ao ativar profissional", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
