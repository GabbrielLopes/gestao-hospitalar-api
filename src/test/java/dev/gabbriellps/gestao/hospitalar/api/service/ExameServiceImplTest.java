package dev.gabbriellps.gestao.hospitalar.api.service;

import dev.gabbriellps.gestao.hospitalar.api.configuration.security.SecurityHelper;
import dev.gabbriellps.gestao.hospitalar.api.dto.request.ExameRequestDTO;
import dev.gabbriellps.gestao.hospitalar.api.dto.response.ExameResponseDTO;
import dev.gabbriellps.gestao.hospitalar.api.enumeration.Especialidade;
import dev.gabbriellps.gestao.hospitalar.api.enumeration.TipoExame;
import dev.gabbriellps.gestao.hospitalar.api.enumeration.TipoUsuario;
import dev.gabbriellps.gestao.hospitalar.api.handler.VidaPlusServiceException;
import dev.gabbriellps.gestao.hospitalar.api.model.*;
import dev.gabbriellps.gestao.hospitalar.api.repository.ExameRepository;
import dev.gabbriellps.gestao.hospitalar.api.service.interfaces.AuditoriaService;
import dev.gabbriellps.gestao.hospitalar.api.service.interfaces.ExameService;
import dev.gabbriellps.gestao.hospitalar.api.service.interfaces.PacienteService;
import dev.gabbriellps.gestao.hospitalar.api.service.interfaces.ProfissionalService;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ExameServiceImplTest {

    @MockitoSpyBean
    private ExameService service;

    @MockitoBean
    private ExameRepository repository;
    @MockitoBean
    private PacienteService pacienteService;
    @MockitoBean
    private ProfissionalService profissionalService;
    @MockitoBean
    private AuditoriaService auditoriaService;
    @MockitoBean
    private SecurityHelper securityHelper;

    private Exame exame;
    private Pessoa pessoaPaciente;
    private Pessoa pessoaMedico;
    private Paciente paciente;
    private ProfissionalSaude profissionalSaude;

    @BeforeEach
    void setUp() throws VidaPlusServiceException {
        when(securityHelper.getUserLogado()).thenReturn(Usuario.builder()
                .role(TipoUsuario.TECNICO_ADM)
                .build());

        pessoaPaciente = Pessoa.builder()
                .id(1L)
                .nome("Josivaldo Teste")
                .cpf("12345678901")
                .email("josivaldo.teste@domain.com")
                .dataNascimento(LocalDate.now().minusYears(30))
                .build();

        paciente = Paciente.builder()
                .id(1L)
                .pessoa(pessoaPaciente)
                .build();

        pessoaMedico = Pessoa.builder()
                .id(2L)
                .nome("Zenilson Teste")
                .cpf("12345678901")
                .email("zenilson.teste@domain.com")
                .dataNascimento(LocalDate.now().minusYears(55))
                .build();

        profissionalSaude = ProfissionalSaude.builder()
                .id(1L)
                .pessoa(pessoaMedico)
                .especialidade(Especialidade.CLINICO_GERAL)
                .registroProfissional("123456")
                .ativo(Boolean.TRUE)
                .dataCriacao(LocalDateTime.now())
                .dataAtualizacao(LocalDateTime.now())
                .build();

        exame = Exame.builder()
                .id(1L)
                .paciente(paciente)
                .profissionalSaude(profissionalSaude)
                .dataHoraExame(LocalDateTime.now().plusDays(1))
                .tipo(TipoExame.SANGUE)
                .resultado("Exame normal")
                .build();
    }

    @Test
    void consultarExames() {
        when(repository.findAll()).thenReturn(Collections.singletonList(exame));

        var exames = service.consultarExames();

        assertNotNull(exames);
        assertFalse(exames.isEmpty());
    }

    @Test
    void consultarExamePorId() throws VidaPlusServiceException {
        when(repository.findById(1L)).thenReturn(Optional.of(exame));

        var exameResponse = service.consultarExamePorId(1L);

        assertNotNull(exameResponse);
    }

    @Test
    void cadastrarExame() throws VidaPlusServiceException {
        ExameRequestDTO exameRequestDTO = ExameRequestDTO.builder()
                .pacienteId(paciente.getId())
                .profissionalSaudeId(profissionalSaude.getId())
                .dataHoraExame(LocalDateTime.now().plusDays(20))
                .tipo(TipoExame.COLONOSCOPIA)
                .build();

        Exame exameSalvo = Exame.builder()
                .id(2L)
                .paciente(paciente)
                .profissionalSaude(profissionalSaude)
                .dataHoraExame(LocalDateTime.now().plusDays(20))
                .tipo(TipoExame.COLONOSCOPIA)
                .build();

        when(pacienteService.findById(Mockito.anyLong())).thenReturn(paciente);
        when(profissionalService.findById(Mockito.anyLong())).thenReturn(profissionalSaude);
        when(repository.saveAndFlush(Mockito.any())).thenReturn(exameSalvo);

        var exameResponse = service.cadastrarExame(exameRequestDTO);

        assertNotNull(exameResponse);
        assertEquals(exameSalvo.toExameResponseDTO(), exameResponse);
        verify(repository).saveAndFlush(any());
        verify(auditoriaService).saveAuditoria(Mockito.any());
    }

    @Test
    void editarExame() throws VidaPlusServiceException {
        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(exame));

        ExameRequestDTO exameRequestDTO = ExameRequestDTO.builder()
                .pacienteId(paciente.getId())
                .profissionalSaudeId(profissionalSaude.getId())
                .dataHoraExame(LocalDateTime.now().plusDays(20))
                .tipo(TipoExame.COLONOSCOPIA)
                .build();

        when(repository.saveAndFlush(Mockito.any())).thenReturn(exame);

        exame.atualizaDadosExame(exameRequestDTO);

        var exameResponse = service.editarExame(1L, exameRequestDTO);

        assertNotNull(exameResponse);
        assertEquals(exame.toExameResponseDTO(), exameResponse);
        verify(repository).saveAndFlush(any());
        verify(auditoriaService).saveAuditoria(Mockito.any());
    }

    @Test
    void excluirExame() throws VidaPlusServiceException {
        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(exame));

        service.excluirExame(1L);

        verify(repository).delete(any());
        verify(auditoriaService).saveAuditoria(Mockito.any());
    }

}