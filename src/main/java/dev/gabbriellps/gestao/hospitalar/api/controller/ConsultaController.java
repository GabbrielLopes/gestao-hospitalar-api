package dev.gabbriellps.gestao.hospitalar.api.controller;

import dev.gabbriellps.gestao.hospitalar.api.dto.response.ConsultaDTO;
import dev.gabbriellps.gestao.hospitalar.api.model.Consulta;
import dev.gabbriellps.gestao.hospitalar.api.service.interfaces.ConsultaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("/consultas")
@RequiredArgsConstructor
public class ConsultaController {

    private final ConsultaService consultaService;

    @GetMapping
    public ResponseEntity<List<ConsultaDTO>> listarConsultas() {
        return ResponseEntity.ok(consultaService.listarConsultas());
    }


}
