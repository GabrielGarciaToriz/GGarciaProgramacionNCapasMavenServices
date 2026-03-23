package com.digis01.GGarciaProgramacionNCapasMavenService.controller;

import com.digis01.GGarciaProgramacionNCapasMavenService.dto.Result;
import com.digis01.GGarciaProgramacionNCapasMavenService.service.CatalogoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/colonia")
@Tag(name = "Catalogo: Colonias", description = "Endpoint para la consulta de las colonias por municipio o codigo postal")
public class ColoniaRestController {

    @Autowired
    private CatalogoService ColoniaService;

    @Operation(
            summary = "Obtener colonias por ID de Municipio",
            description = "Recupera todas las colonias asociadas a un municpio especifico")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Colonias recuperadas correctamente",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(
                                        implementation = Result.class))),
                @ApiResponse(
                        responseCode = "204",
                        description = "El municipio no tiene colonias registradas",
                        content = @Content),
                @ApiResponse(
                        responseCode = "400",
                        description = "Error en la peticion",
                        content = @Content),
                @ApiResponse(
                        responseCode = "500",
                        description = "Error interno del servidor",
                        content = @Content)
            })
    @GetMapping("/{idMunicipio}")
    public ResponseEntity<Result> GetById(
            @Parameter(description = "ID del municipio a consultar", example = "15")
            @PathVariable("idMunicipio") int IdMunicipio) {
        Result result = ColoniaService.getColoniaByMunicipio(IdMunicipio);
        return new ResponseEntity<>(result, result.correct ? HttpStatus.OK : HttpStatus.BAD_REQUEST);

    }

    @Operation(
            summary = "Obtener colonias por el CodigoPostal",
            description = "Recupera todas las colonias que comparte un mismo codigo postal")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Colonais recuperadas exitosamente",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = Result.class))),
                @ApiResponse(
                        responseCode = "204",
                        description = "No se encontraron colonias con ese codigo postal",
                        content = @Content),
                @ApiResponse(
                        responseCode = "400",
                        description = "Error en la peticion",
                        content = @Content),
                @ApiResponse(
                        responseCode = "500",
                        description = "Error interno del servidor",
                        content = @Content
                )
            })

    @GetMapping("/codigoPostal/{codigoPostal}")
    public ResponseEntity<Result> GetByCodigoPostal(
            @Parameter(
                    description = "Codigo postal con 5 digitos",
                    example = "57710")
            @PathVariable("codigoPostal") String CodigoPostal) {
        Result result = ColoniaService.getColoniaByCP(CodigoPostal);
        return new ResponseEntity<>(result, result.correct ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}
