package dev.gabbriellps.gestao.hospitalar.api.controller;

import dev.gabbriellps.gestao.hospitalar.api.dto.request.ProfissionalRequestDTO;
import dev.gabbriellps.gestao.hospitalar.api.dto.response.ProfissionalResponseDTO;
import dev.gabbriellps.gestao.hospitalar.api.handler.ErrorResponse;
import dev.gabbriellps.gestao.hospitalar.api.handler.VidaPlusServiceException;
import dev.gabbriellps.gestao.hospitalar.api.service.interfaces.ProfissionalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/profissionais-saudes")
@RequiredArgsConstructor
public class ProfissionalSaudeController {

    private final ProfissionalService profissionalService;


    @Operation(summary = "Consultar todos profissionais de saúde.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso.",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ProfissionalResponseDTO.class)))
                    }),
            @ApiResponse(responseCode = "400", description = "Validação dos dados de request.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Profissional de saúde não encontrado.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping
    public ResponseEntity<List<ProfissionalResponseDTO>> consultarProfissionais() {
        return ResponseEntity.ok(profissionalService.consultarProfissionais());
    }


    @Operation(summary = "Consultar profissional de saúde por ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProfissionalResponseDTO.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Validação dos dados de request.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(hidden = true))}),
            @ApiResponse(responseCode = "404", description = "Profissional de saúde não encontrado.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProfissionalResponseDTO> consultarProfissionalSaudePorId(
            @PathVariable("id") Long id
    ) throws VidaPlusServiceException {
        return ResponseEntity.ok(profissionalService.consultarProfissionalSaudePorId(id));
    }

    @Operation(summary = "Cadastrar profissional de saúde.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cadastro realizado com sucesso.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProfissionalResponseDTO.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Validação dos dados de request.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Profissional de saúde não encontrado.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PostMapping
    public ResponseEntity<ProfissionalResponseDTO> cadastrarProfissionalSaude(
            @Valid @RequestBody ProfissionalRequestDTO requestDTO
    ) throws VidaPlusServiceException {
        return ResponseEntity.ok(profissionalService.cadastrarProfissionalSaude(requestDTO));
    }

    @Operation(summary = "Editar profissional de saúde.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Edição realizada com sucesso.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProfissionalResponseDTO.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Validação dos dados de request.",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Profissional de saúde não encontrado.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProfissionalResponseDTO> editarProfissionalSaude(
            @PathVariable("id") Long id,
            @Valid @RequestBody ProfissionalRequestDTO requestDTO
    ) throws VidaPlusServiceException {
        return ResponseEntity.ok(profissionalService.editarProfissionalSaude(id, requestDTO));
    }


    @Operation(summary = "Excluir profissional de saúde.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Exclusão realizada com sucesso.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(hidden = true))
                    }),
            @ApiResponse(responseCode = "400", description = "Validação dos dados de request.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Profissional de saúde não encontrado.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirProfissionalSaude(
            @PathVariable("id") Long id
    ) throws VidaPlusServiceException {
        profissionalService.inativarProfissionalSaude(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Ativar profissional de saúde.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ativação realizada com sucesso.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(hidden = true))
                    }),
            @ApiResponse(responseCode = "400", description = "Validação dos dados de request.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Profissional de saúde não encontrado.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PostMapping("/{id}")
    public ResponseEntity<?> ativarProfissionalSaude(
            @PathVariable("id") Long id
    ) throws VidaPlusServiceException {
        profissionalService.ativarProfissionalSaude(id);
        return ResponseEntity.ok().build();
    }


}
