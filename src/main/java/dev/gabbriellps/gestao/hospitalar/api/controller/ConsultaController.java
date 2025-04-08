package dev.gabbriellps.gestao.hospitalar.api.controller;

import dev.gabbriellps.gestao.hospitalar.api.dto.response.ConsultaResponseDTO;
import dev.gabbriellps.gestao.hospitalar.api.handler.ErrorResponse;
import dev.gabbriellps.gestao.hospitalar.api.service.interfaces.ConsultaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Listar todas as consultas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Consulta realizada com sucesso.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ConsultaResponseDTO.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Validação dos dados de request.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Consulta não encontrada.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping
    public ResponseEntity<List<ConsultaResponseDTO>> listarConsultas() {
        return ResponseEntity.ok(consultaService.listarConsultas());
    }


}
