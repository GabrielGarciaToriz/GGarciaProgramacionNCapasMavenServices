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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/estado")
@Tag(name = "Catalogo: Estado", description = "Endpoint para la consulta y gestion del catalgo de esatdos")
public class EstadoRestController {

    @Autowired
    private CatalogoService EstadoService;

    @Operation(summary = "Obtener los estados por el identificador (ID) del pais", description = "Recuperar todos los estados que pertenecesn a n pais en especifico con base en el ID)")
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "Lista de esatdos recuperada con exito",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Result.class))),
        @ApiResponse(
                responseCode = "204",
                description = "El pais existe, pero no tienen estados asociados",
                content = @Content),
        @ApiResponse(
                responseCode = "400",
                description = "Error en la peticion, el id no corresponse a un pais registrado",
                content = @Content),
        @ApiResponse(
                responseCode = "500",
                description = "Error interno del servidor",
                content = @Content)
    })
    @GetMapping("/{idPais}")
    public ResponseEntity<Result> GetAllById(
            @Parameter(description = "Identificador unico del pais que se desea consultar los estados", example = "1")
            @PathVariable("idPais") int idPais) {

        Result result = EstadoService.getEstadosByPais(idPais);
        return new ResponseEntity<>(result, result.correct ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

}
