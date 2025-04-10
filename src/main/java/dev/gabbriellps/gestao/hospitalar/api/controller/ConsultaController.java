package dev.gabbriellps.gestao.hospitalar.api.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.gabbriellps.gestao.hospitalar.api.dto.request.ConsultaRequestDTO;
import dev.gabbriellps.gestao.hospitalar.api.dto.response.ConsultaResponseDTO;
import dev.gabbriellps.gestao.hospitalar.api.handler.ErrorResponse;
import dev.gabbriellps.gestao.hospitalar.api.handler.VidaPlusServiceException;
import dev.gabbriellps.gestao.hospitalar.api.service.interfaces.ConsultaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Validated
@RestController
@RequestMapping("/consultas")
@RequiredArgsConstructor
public class ConsultaController {

    private final ConsultaService consultaService;

    @Operation(summary = "Listar todas as consultas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso.",
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

    @Operation(summary = "Listar consulta por ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso.",
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
    @GetMapping("/{id}")
    public ResponseEntity<ConsultaResponseDTO> listarConsultaPorId(
            @PathVariable("id") Long id
    ) throws VidaPlusServiceException {
        return ResponseEntity.ok(consultaService.listarConsultaPorId(id));
    }
    @Operation(summary = "Listar consulta por periodo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso.",
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
    @GetMapping("/periodo")
    public ResponseEntity<List<ConsultaResponseDTO>> listarConsultaPorId(
            @JsonFormat(pattern = "dd/MM/yyyy")
            @RequestParam("dataInicio") LocalDate dataInicio,
            @JsonFormat(pattern = "dd/MM/yyyy")
            @RequestParam("dataFim") LocalDate dataFim
    ) throws VidaPlusServiceException {
        return ResponseEntity.ok(consultaService.listarConsultasPorPeriodo(dataInicio, dataFim));
    }

    @Operation(summary = "Cadastrar consulta.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Consulta cadastrada com sucesso.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ConsultaResponseDTO.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Validação dos dados de request.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PostMapping
    public ResponseEntity<ConsultaResponseDTO> cadastrarConsulta(
            @Valid @RequestBody ConsultaRequestDTO consultaRequestDTO
    ) throws VidaPlusServiceException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(consultaService.cadastrarConsulta(consultaRequestDTO));
    }

    @Operation(summary = "Atualizar consulta.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta atualizada com sucesso.",
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
    @PutMapping("/{id}")
    public ResponseEntity<ConsultaResponseDTO> editarConsulta(
            @PathVariable("id") Long id,
            @Valid @RequestBody ConsultaRequestDTO consultaRequestDTO
    ) throws VidaPlusServiceException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(consultaService.atualizarConsulta(id, consultaRequestDTO));
    }

    @Operation(summary = "Excluir consulta.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Consulta excluída com sucesso.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(hidden = true))
                    }),
            @ApiResponse(responseCode = "400", description = "Validação dos dados de request.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Consulta não encontrada.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirConsulta(
            @PathVariable("id") Long id) throws VidaPlusServiceException {
        consultaService.excluirConsulta(id);
        return ResponseEntity.noContent().build();
    }


}
