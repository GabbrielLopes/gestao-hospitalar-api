package dev.gabbriellps.gestao.hospitalar.api.service;

import dev.gabbriellps.gestao.hospitalar.api.configuration.security.SecurityHelper;
import dev.gabbriellps.gestao.hospitalar.api.dto.request.PessoaRequestDTO;
import dev.gabbriellps.gestao.hospitalar.api.dto.request.ProfissionalRequestDTO;
import dev.gabbriellps.gestao.hospitalar.api.enumeration.Especialidade;
import dev.gabbriellps.gestao.hospitalar.api.enumeration.Sexo;
import dev.gabbriellps.gestao.hospitalar.api.enumeration.TipoUsuario;
import dev.gabbriellps.gestao.hospitalar.api.handler.VidaPlusServiceException;
import dev.gabbriellps.gestao.hospitalar.api.model.Pessoa;
import dev.gabbriellps.gestao.hospitalar.api.model.ProfissionalSaude;
import dev.gabbriellps.gestao.hospitalar.api.model.Usuario;
import dev.gabbriellps.gestao.hospitalar.api.repository.ProfissionalSaudeRepository;
import dev.gabbriellps.gestao.hospitalar.api.service.interfaces.AuditoriaService;
import dev.gabbriellps.gestao.hospitalar.api.service.interfaces.ProfissionalService;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ProfissionalServiceImplTest {

    @MockitoSpyBean
    private ProfissionalService service;

    @MockitoBean
    private ProfissionalSaudeRepository repository;
    @MockitoBean
    private AuditoriaService auditoriaService;
    @MockitoBean
    private SecurityHelper securityHelper;

    private ProfissionalSaude profissionalSaude;
    private Pessoa pessoa;
    private ProfissionalRequestDTO profissionalRequestDTO;
    private PessoaRequestDTO pessoaRequestDTO;
    private Usuario usuario;

    @BeforeEach
    void testsetUp() throws VidaPlusServiceException {

        when(securityHelper.getUserLogado()).thenReturn(Usuario.builder()
                .id(1L)
                .login("userTest")
                .password(UUID.randomUUID().toString())
                .role(TipoUsuario.TECNICO_ADM)
                .dataCriacao(LocalDateTime.now().minusDays(5))
                .build());

        pessoaRequestDTO = PessoaRequestDTO.builder()
                .nome("Pessoa Teste")
                .cpf("12345678901")
                .email("email.teste@domain.com")
                .dataNascimento(LocalDate.now().minusYears(30))
                .cep("12345678")
                .sexo(Sexo.M)
                .telefone("1234567890")
                .complemento("Complemento Teste")
                .endereco("Endereço Teste")
                .build();

        pessoa = Pessoa.builder()
                .id(1L)
                .nome("Nome Teste")
                .cpf("12345678901")
                .email("email.teste@domain.com")
                .dataNascimento(LocalDate.now().minusYears(30))
                .build();

        profissionalSaude = ProfissionalSaude.builder()
                .id(1L)
                .pessoa(pessoa)
                .especialidade(Especialidade.CLINICO_GERAL)
                .registroProfissional("123456")
                .ativo(Boolean.TRUE)
                .dataCriacao(LocalDateTime.now())
                .dataAtualizacao(LocalDateTime.now())
                .build();

        profissionalRequestDTO = ProfissionalRequestDTO.builder()
                .pessoa(pessoaRequestDTO)
                .especialidade(Especialidade.CLINICO_GERAL)
                .registroProfissional("123456")
                .build();
    }

    @Test
    void testConsultarProfissionais() {
        when(repository.findAll()).thenReturn(Collections.singletonList(profissionalSaude));

        var response = service.consultarProfissionais();

        assertNotNull(response);
        assertFalse(response.isEmpty());
    }

    @Test
    void testConsultarProfissionalSaudePorId() throws VidaPlusServiceException {
        when(repository.findById(1L)).thenReturn(java.util.Optional.of(profissionalSaude));

        var response = service.consultarProfissionalSaudePorId(1L);

        assertNotNull(response);
        assertEquals(profissionalSaude.getId(), response.getId());
    }

    @Test
    void testCadastrarProfissionalSaude() throws VidaPlusServiceException {
        when(repository.saveAndFlush(Mockito.any())).thenReturn(profissionalSaude);

        var response = service.cadastrarProfissionalSaude(profissionalRequestDTO);

        assertNotNull(response);
        verify(repository).saveAndFlush(Mockito.any());
        verify(auditoriaService).saveAuditoria(Mockito.any());
    }

    @Test
    void testCadastrarProfissionalSaudeError() {
        when(repository.findById(1L)).thenReturn(Optional.of(profissionalSaude));
        when(repository.saveAndFlush(Mockito.any())).thenThrow(DataIntegrityViolationException.class);

        assertThrows(VidaPlusServiceException.class, () -> {
            service.cadastrarProfissionalSaude(profissionalRequestDTO);
        });
    }

    @Test
    void testEditarProfissionalSaude() throws VidaPlusServiceException {
        profissionalRequestDTO.setRegistroProfissional("654321");

        when(repository.findById(1L)).thenReturn(Optional.of(profissionalSaude));

        profissionalSaude.atualizaDadosPessoa(profissionalRequestDTO);

        when(repository.saveAndFlush(Mockito.any())).thenReturn(profissionalSaude);

        var response = service.editarProfissionalSaude(1L, profissionalRequestDTO);

        assertNotNull(response);
        verify(repository).saveAndFlush(Mockito.any());
        verify(auditoriaService).saveAuditoria(Mockito.any());
        assertEquals(profissionalSaude.getRegistroProfissional(), response.getRegistroProfissional());
    }

    @Test
    void testEditarProfissionalSaudeError() {
        when(repository.findById(1L)).thenReturn(Optional.of(profissionalSaude));
        when(repository.saveAndFlush(Mockito.any())).thenThrow(DataIntegrityViolationException.class);

        assertThrows(VidaPlusServiceException.class, () -> {
            service.editarProfissionalSaude(1L, profissionalRequestDTO);
        });
    }

    @Test
    void testInativarProfissionalSaude() throws VidaPlusServiceException {
        when(repository.findById(1L)).thenReturn(Optional.of(profissionalSaude));

        service.inativarProfissionalSaude(1L);

        verify(repository).saveAndFlush(Mockito.any());
        verify(auditoriaService).saveAuditoria(Mockito.any());
    }

    @Test
    void testInativarProfissionalSaudeError()  {
        when(repository.findById(1L)).thenReturn(Optional.of(profissionalSaude));
        when(repository.saveAndFlush(Mockito.any())).thenThrow(DataIntegrityViolationException.class);

        assertThrows(VidaPlusServiceException.class, () -> {
            service.inativarProfissionalSaude(1L);
        });
    }

    @Test
    void testAtivarProfissionalSaude() throws VidaPlusServiceException {
        when(repository.findById(1L)).thenReturn(Optional.of(profissionalSaude));

        service.ativarProfissionalSaude(1L);

        verify(repository).saveAndFlush(Mockito.any());
        verify(auditoriaService).saveAuditoria(Mockito.any());
    }

    @Test
    void testAtivarProfissionalSaudeError() {
        when(repository.findById(1L)).thenReturn(Optional.of(profissionalSaude));
        when(repository.saveAndFlush(Mockito.any())).thenThrow(DataIntegrityViolationException.class);

        assertThrows(VidaPlusServiceException.class, () -> {
            service.ativarProfissionalSaude(1L);
        });
    }

    @Test
    void testFindById() throws VidaPlusServiceException {
        when(repository.findById(1L)).thenReturn(java.util.Optional.of(profissionalSaude));

        var response = service.findById(1L);

        assertNotNull(response);
        assertEquals(profissionalSaude.toProfissionalSaudeResponseDTO(), response.toProfissionalSaudeResponseDTO());
    }

    @Test
    void testConsultarProfissionalPorFiltro() {
        when(repository.buscaProfissionaisPorFiltro(Mockito.anyString(), Mockito.any()))
                .thenReturn(Collections.singletonList(profissionalSaude));

        var response = service.consultarProfissionalPorFiltro("12345678901", Especialidade.CLINICO_GERAL);

        assertNotNull(response);
        assertFalse(response.isEmpty());
    }
}