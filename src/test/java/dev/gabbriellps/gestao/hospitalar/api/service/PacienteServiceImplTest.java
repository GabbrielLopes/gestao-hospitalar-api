package dev.gabbriellps.gestao.hospitalar.api.service;

import dev.gabbriellps.gestao.hospitalar.api.configuration.security.SecurityHelper;
import dev.gabbriellps.gestao.hospitalar.api.dto.request.PacienteRequestDTO;
import dev.gabbriellps.gestao.hospitalar.api.dto.request.PessoaRequestDTO;
import dev.gabbriellps.gestao.hospitalar.api.enumeration.Sexo;
import dev.gabbriellps.gestao.hospitalar.api.enumeration.TipoUsuario;
import dev.gabbriellps.gestao.hospitalar.api.handler.VidaPlusServiceException;
import dev.gabbriellps.gestao.hospitalar.api.model.Paciente;
import dev.gabbriellps.gestao.hospitalar.api.model.Pessoa;
import dev.gabbriellps.gestao.hospitalar.api.model.Usuario;
import dev.gabbriellps.gestao.hospitalar.api.repository.PacienteRepository;
import dev.gabbriellps.gestao.hospitalar.api.service.interfaces.AuditoriaService;
import dev.gabbriellps.gestao.hospitalar.api.service.interfaces.PacienteService;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class PacienteServiceImplTest {

    @MockitoSpyBean
    private PacienteService service;

    @MockitoBean
    private PacienteRepository repository;
    @MockitoBean
    private AuditoriaService auditoriaService;
    @MockitoBean
    private SecurityHelper securityHelper;


    private Paciente paciente;
    private Pessoa pessoa;
    private PacienteRequestDTO pacienteRequestDTO;
    private PessoaRequestDTO pessoaRequestDTO;

    @BeforeEach
    void setUp() throws VidaPlusServiceException {
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

        pacienteRequestDTO = PacienteRequestDTO.builder()
                .pessoa(pessoaRequestDTO)
                .build();

        pessoa = Pessoa.builder()
                .id(1L)
                .nome("Nome Teste")
                .cpf("12345678901")
                .email("email.teste@domain.com")
                .dataNascimento(LocalDate.now().minusYears(30))
                .build();

        paciente = Paciente.builder()
                .id(1L)
                .pessoa(pessoa)
                .build();
    }

    @Test
    void consultarPacientes() {
        // Mockando o retorno do repositório
        when(repository.findAll()).thenReturn(Collections.singletonList(paciente));

        // Chamando o método a ser testado
        var pacientes = service.consultarPacientes();

        // Verificando se o retorno não é nulo / vazio e se contém o paciente esperado
        assertNotNull(pacientes);
        assertFalse(pacientes.isEmpty());
        assertEquals(paciente.toPacienteResponseDTO(), pacientes.get(0));
    }

    @Test
    void consultarPacientePorId() throws VidaPlusServiceException {
        when(repository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(paciente));

        var pacienteResponse = service.consultarPacientePorId(1L);

        assertNotNull(pacienteResponse);
        assertEquals(paciente.toPacienteResponseDTO(), pacienteResponse);
    }

    @Test
    void cadastrarPaciente() throws VidaPlusServiceException {

        var pacienteResponse = service.cadastrarPaciente(pacienteRequestDTO);

        assertNotNull(pacienteResponse);
        verify(repository).saveAndFlush(Mockito.any());
        verify(auditoriaService).saveAuditoria(Mockito.any());
    }

    @Test
    void cadastrarPacienteComDadosPessoaInvalido() {
        pacienteRequestDTO.getPessoa().setCpf(null);

        assertThrows(VidaPlusServiceException.class, () -> {
            service.cadastrarPaciente(pacienteRequestDTO);
        });
    }

    @Test
    void cadastrarPacienteError() {
        when(repository.saveAndFlush(Mockito.any(Paciente.class))).thenThrow(DataIntegrityViolationException.class);

        assertThrows(VidaPlusServiceException.class, () -> {
            service.cadastrarPaciente(pacienteRequestDTO);
        });
    }

    @Test
    void editarPaciente() throws VidaPlusServiceException {

        when(repository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(paciente));
        when(repository.saveAndFlush(Mockito.any(Paciente.class))).thenReturn(paciente);

        var pacienteResponse = service.editarPaciente(1L, pacienteRequestDTO);

        assertNotNull(pacienteResponse);
        assertEquals(paciente.toPacienteResponseDTO(), pacienteResponse);
        verify(repository).saveAndFlush(Mockito.any());
        verify(auditoriaService).saveAuditoria(Mockito.any());
    }

    @Test
    void editarPacienteError() {
        when(repository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(paciente));
        when(repository.saveAndFlush(Mockito.any(Paciente.class))).thenThrow(DataIntegrityViolationException.class);

        assertThrows(VidaPlusServiceException.class, () -> {
            service.editarPaciente(1L, pacienteRequestDTO);
        });

    }

    @Test
    void findById() throws VidaPlusServiceException {
        when(repository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(paciente));

        var pacienteResponse = service.findById(1L);

        assertNotNull(pacienteResponse);
        assertEquals(paciente, pacienteResponse);
    }

    @Test
    void consultarPacienteComFiltroCpf() throws VidaPlusServiceException {
        when(repository.buscaPorFiltro(Mockito.anyString())).thenReturn(Collections.singletonList(paciente));

        var pacientes = service.consultarPacienteComFiltro("1234567");

        assertNotNull(pacientes);
        assertFalse(pacientes.isEmpty());
        assertEquals(paciente.toPacienteResponseDTO(), pacientes.get(0));
    }

    @Test
    void consultarPacienteComFiltroNome() throws VidaPlusServiceException {
        when(repository.buscaPorFiltro(Mockito.anyString())).thenReturn(Collections.singletonList(paciente));

        var pacientes = service.consultarPacienteComFiltro("Nome");

        assertNotNull(pacientes);
        assertFalse(pacientes.isEmpty());
        assertEquals(paciente.toPacienteResponseDTO(), pacientes.get(0));
    }

    @Test
    void consultarPacienteComFiltroEmail() throws VidaPlusServiceException {
        when(repository.buscaPorFiltro(Mockito.anyString())).thenReturn(Collections.singletonList(paciente));

        var pacientes = service.consultarPacienteComFiltro("email.teste");

        assertNotNull(pacientes);
        assertFalse(pacientes.isEmpty());
        assertEquals(paciente.toPacienteResponseDTO(), pacientes.get(0));
    }

    @Test
    void inativarPaciente() throws VidaPlusServiceException {
        when(repository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(paciente));

        service.inativarPaciente(1L);

        verify(repository).saveAndFlush(Mockito.any());
        verify(auditoriaService).saveAuditoria(Mockito.any());
    }

    @Test
    void inativarPacienteError() {
        when(repository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(paciente));
        when(repository.saveAndFlush(Mockito.any(Paciente.class))).thenThrow(DataIntegrityViolationException.class);

        assertThrows(VidaPlusServiceException.class, () -> {
            service.inativarPaciente(1L);
        });
    }

    @Test
    void ativarPaciente() throws VidaPlusServiceException {
        when(repository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(paciente));
        when(repository.saveAndFlush(Mockito.any(Paciente.class))).thenReturn(paciente);

        service.ativarPaciente(1L);

        verify(repository).saveAndFlush(Mockito.any());
        verify(auditoriaService).saveAuditoria(Mockito.any());
    }

    @Test
    void ativarPacienteInexistente() {
        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        assertThrows(VidaPlusServiceException.class, () -> {
            service.ativarPaciente(1L);
        });
    }

    @Test
    void ativarPacienteError() {
        when(repository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(paciente));
        when(repository.saveAndFlush(Mockito.any(Paciente.class))).thenThrow(DataIntegrityViolationException.class);

        assertThrows(VidaPlusServiceException.class, () -> {
            service.ativarPaciente(1L);
        });
    }

}