package com.digis01.GGarciaProgramacionNCapasMavenService.Controller;

import com.digis01.GGarciaProgramacionNCapasMavenService.DTO.Result;
import com.digis01.GGarciaProgramacionNCapasMavenService.Service.CatalogoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.internal.bytebuddy.implementation.Implementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/municipio")
@Tag(name = "Catalogo: Municipios", description = "Endpoint para la consulta de municipios con base en el identificador (ID) de un estado")
public class MunicipioRestController {

    @Autowired
    private CatalogoService MunicipioService;

    @Operation(summary = "Obtener municipios con base en el ID de Estado",
            description = "Recuperar la lista de municipios que pertenecen a un estado especifico.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                description = "Municipios recuperados con exito",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = Result.class))),
        @ApiResponse(responseCode = "204",
                description = "El estado no tiene municipios registrados",
                content = @Content),
        @ApiResponse(responseCode = "400",
                description = "Error en la peticion, el ID es invalido",
                content = @Content),
        @ApiResponse(responseCode = "500",
                description = "Error interno del servidor",
                content = @Content)
    })

    @GetMapping("/{idEstado}")
    public ResponseEntity<Result> GetAllById(
            @Parameter(
                    description = "ID del estado a consultar",
                    example = "3")
            @PathVariable("idEstado") int idEstado) {
        Result result = MunicipioService.getMunicipioByEstado(idEstado);
        return new ResponseEntity<>(result, result.correct ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

}
