package dev.gabbriellps.gestao.hospitalar.api.controller;

import dev.gabbriellps.gestao.hospitalar.api.dto.request.ExameRequestDTO;
import dev.gabbriellps.gestao.hospitalar.api.dto.response.ConsultaResponseDTO;
import dev.gabbriellps.gestao.hospitalar.api.dto.response.ExameResponseDTO;
import dev.gabbriellps.gestao.hospitalar.api.handler.ErrorResponse;
import dev.gabbriellps.gestao.hospitalar.api.handler.VidaPlusServiceException;
import dev.gabbriellps.gestao.hospitalar.api.service.interfaces.ExameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/exames")
@RequiredArgsConstructor
public class ExameController {

    private final ExameService exameService;

    @Operation(summary = "Consultar todos os exames.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ConsultaResponseDTO.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Validação dos dados de request.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Exame não encontrado.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping
    public ResponseEntity<List<ExameResponseDTO>> consultarExames() {
        return ResponseEntity.ok(exameService.consultarExames());
    }

    @Operation(summary = "Consultar exame por ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ConsultaResponseDTO.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Validação dos dados de request.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Exame não encontrado.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping("/{id}")
    public ResponseEntity<ExameResponseDTO> consultarExamePorId(
            @PathVariable("id") Long id
    ) throws VidaPlusServiceException {
        return ResponseEntity.ok(exameService.consultarExamePorId(id));
    }

    @Operation(summary = "Cadastrar exame.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Exame cadastrado com sucesso.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ConsultaResponseDTO.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Validação dos dados de request.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PostMapping
    public ResponseEntity<ExameResponseDTO> cadastrarExame(
            @Valid @RequestBody ExameRequestDTO requestDTO
    ) throws VidaPlusServiceException {
        return ResponseEntity.status(HttpStatus.CREATED).body(exameService.cadastrarExame(requestDTO));
    }

    @Operation(summary = "Editar exame.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exame cadastrado com sucesso.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ConsultaResponseDTO.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Validação dos dados de request.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Exame não encontrado.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PutMapping("/{id}")
    public ResponseEntity<ExameResponseDTO> editarExame(
            @PathVariable("id") Long id,
            @Valid @RequestBody ExameRequestDTO requestDTO
    ) throws VidaPlusServiceException {
        return ResponseEntity.ok(exameService.editarExame(id, requestDTO));
    }

    @Operation(summary = "Excluir exame.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Exame excluido com sucesso.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ConsultaResponseDTO.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Validação dos dados de request.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Exame não encontrado.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ExameResponseDTO> excluirExame(
            @PathVariable("id") Long id
    ) throws VidaPlusServiceException {
        exameService.excluirExame(id);
        return ResponseEntity.noContent().build();
    }


}
