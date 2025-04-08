package dev.gabbriellps.gestao.hospitalar.api.controller;

import dev.gabbriellps.gestao.hospitalar.api.dto.request.PacienteRequestDTO;
import dev.gabbriellps.gestao.hospitalar.api.dto.response.PacienteResponseDTO;
import dev.gabbriellps.gestao.hospitalar.api.handler.VidaPlusServiceException;
import dev.gabbriellps.gestao.hospitalar.api.service.interfaces.PacienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;


    @GetMapping
    public ResponseEntity<List<PacienteResponseDTO>> consultarPacientes(){
        return ResponseEntity.ok(pacienteService.consultarPacientes());
    }


    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> consultarPacientePorId(
            @PathVariable("id") Long id
    ) throws VidaPlusServiceException {
        return ResponseEntity.ok(pacienteService.consultarPacientePorId(id));
    }

    @PostMapping
    public ResponseEntity<PacienteResponseDTO> cadastrarPaciente(
            @Valid @RequestBody PacienteRequestDTO requestDTO
    ) throws VidaPlusServiceException {
        return ResponseEntity.ok(pacienteService.cadastrarPaciente(requestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> editarPaciente(
            @PathVariable("id") Long id,
            @Valid @RequestBody PacienteRequestDTO requestDTO
    ) throws VidaPlusServiceException {
        return ResponseEntity.ok(pacienteService.editarPaciente(id, requestDTO));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirPaciente(
            @PathVariable("id") Long id
    ) throws VidaPlusServiceException {
        pacienteService.inativarPaciente(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> ativarPaciente(
            @PathVariable("id") Long id
    ) throws VidaPlusServiceException {
        pacienteService.ativarPaciente(id);
        return ResponseEntity.ok().build();
    }


}
