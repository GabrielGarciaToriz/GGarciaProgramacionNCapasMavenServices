package com.digis01.GGarciaProgramacionNCapasMavenService.Controller;

import com.digis01.GGarciaProgramacionNCapasMavenService.DAO.JPA.ColoniaDAOJPAImplementation;
import com.digis01.GGarciaProgramacionNCapasMavenService.Entity.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ColoniaDAOJPAImplementation ColoniaDAOJPA;

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
    public ResponseEntity GetById(
            @Parameter(description = "ID del municipio a consultar", example = "15")
            @PathVariable("idMunicipio") int IdMunicipio) {
        try {
            Result result = ColoniaDAOJPA.GetAllById(IdMunicipio);
            if (result.correct) {
                if (result.objects != null && !result.objects.isEmpty()) {
                    return ResponseEntity.ok(result);
                } else {
                    return ResponseEntity.noContent().build();
                }
            } else {
                return ResponseEntity.badRequest().body(result.errorMessage);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e);
        }
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
    public ResponseEntity GetByCodigoPostal(
            @Parameter(
                    description = "Codigo postal con 5 digitos",
                    example="57710")
            @PathVariable("codigoPostal") String CodigoPostal) {
        try {
            Result result = ColoniaDAOJPA.GetByCodigoPostal(CodigoPostal);
            if (result.correct) {
                if (result.objects != null && !result.objects.isEmpty()) {
                    return ResponseEntity.ok(result);
                } else {
                    return ResponseEntity.noContent().build();
                }
            } else {
                return ResponseEntity.badRequest().body(result.errorMessage);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e);
        }
    }
}
