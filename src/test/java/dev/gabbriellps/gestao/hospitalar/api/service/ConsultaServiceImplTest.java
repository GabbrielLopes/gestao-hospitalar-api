package dev.gabbriellps.gestao.hospitalar.api.service;

import dev.gabbriellps.gestao.hospitalar.api.configuration.security.SecurityHelper;
import dev.gabbriellps.gestao.hospitalar.api.dto.request.ConsultaRequestDTO;
import dev.gabbriellps.gestao.hospitalar.api.enumeration.Especialidade;
import dev.gabbriellps.gestao.hospitalar.api.enumeration.TipoUsuario;
import dev.gabbriellps.gestao.hospitalar.api.handler.VidaPlusServiceException;
import dev.gabbriellps.gestao.hospitalar.api.model.*;
import dev.gabbriellps.gestao.hospitalar.api.repository.ConsultaRepository;
import dev.gabbriellps.gestao.hospitalar.api.service.interfaces.AuditoriaService;
import dev.gabbriellps.gestao.hospitalar.api.service.interfaces.ConsultaService;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ConsultaServiceImplTest {

    @MockitoSpyBean
    private ConsultaService service;

    @MockitoBean
    private ConsultaRepository repository;
    @MockitoBean
    private PacienteService pacienteService;
    @MockitoBean
    private ProfissionalService profissionalService;
    @MockitoBean
    private AuditoriaService auditoriaService;
    @MockitoBean
    private SecurityHelper securityHelper;

    private Consulta consulta;
    private Pessoa pessoaPaciente;
    private Pessoa pessoaMedico;
    private Paciente paciente;
    private ProfissionalSaude profissionalSaude;

    @BeforeEach
    void testsetUp() throws VidaPlusServiceException {
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

        consulta = Consulta.builder()
                .id(1L)
                .paciente(paciente)
                .profissionalSaude(profissionalSaude)
                .dataHoraConsulta(LocalDateTime.now().minusDays(5))
                .build();


    }

    @Test
    void testListarConsultas() {
        when(repository.findAll()).thenReturn(Collections.singletonList(consulta));

        var response = service.listarConsultas();

        assertNotNull(response);
        assertFalse(response.isEmpty());
    }

    @Test
    void testListarConsultaPorId() throws VidaPlusServiceException {
        when(repository.findById(1L)).thenReturn(java.util.Optional.of(consulta));

        var response = service.listarConsultaPorId(1L);

        assertNotNull(response);
        assertEquals(consulta.getId(), response.getId());
    }

    @Test
    void testCadastrarConsulta() throws VidaPlusServiceException {
        when(pacienteService.findById(Mockito.anyLong())).thenReturn(paciente);
        when(profissionalService.findById(Mockito.anyLong())).thenReturn(profissionalSaude);
        when(repository.saveAndFlush(Mockito.any())).thenReturn(consulta);

        var response = service.cadastrarConsulta(ConsultaRequestDTO.builder()
                .pacienteId(1L)
                .profissionalSaudeId(1L)
                .dataHoraConsulta(LocalDateTime.now().minusDays(5))
                .build());

        assertNotNull(response);
    }

    @Test
    void testAtualizarConsulta() throws VidaPlusServiceException {
        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(consulta));
        when(repository.saveAndFlush(Mockito.any())).thenReturn(consulta);

        ConsultaRequestDTO requestDTO = ConsultaRequestDTO.builder()
                .pacienteId(1L)
                .profissionalSaudeId(1L)
                .dataHoraConsulta(LocalDateTime.now().minusDays(2))
                .build();

        var response = service.atualizarConsulta(1L, requestDTO);

        assertNotNull(response);
        assertEquals(consulta.getId(), response.getId());
        assertEquals(response.getDataHoraConsulta(), requestDTO.getDataHoraConsulta());
        verify(repository).saveAndFlush(Mockito.any());
        verify(auditoriaService).saveAuditoria(Mockito.any());
    }

    @Test
    void testExcluirConsulta() throws VidaPlusServiceException {
        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(consulta));

        service.excluirConsulta(1L);

        Mockito.verify(repository).delete(Mockito.any());
        Mockito.verify(auditoriaService).saveAuditoria(Mockito.any());
    }

    @Test
    void testListarConsultasPorPeriodo() throws VidaPlusServiceException {
        when(repository.buscaConsultasPorPeriodo(Mockito.any(), Mockito.any()))
                .thenReturn(Collections.singletonList(consulta));

        var response = service.listarConsultasPorPeriodo(LocalDate.now().minusDays(10), LocalDate.now());

        assertNotNull(response);
        assertFalse(response.isEmpty());
    }
}
