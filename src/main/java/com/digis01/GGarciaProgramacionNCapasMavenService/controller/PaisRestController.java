package com.digis01.GGarciaProgramacionNCapasMavenService.controller;

import com.digis01.GGarciaProgramacionNCapasMavenService.dto.Result;
import com.digis01.GGarciaProgramacionNCapasMavenService.service.CatalogoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/pais")
@Tag(name = "Catalogo: Pais", description = "EndPoint para la consulta y gestion del catalogo de paises")
public class PaisRestController {

    @Autowired
    private CatalogoService PaisService;

    @Operation(summary = " Obtener todos los paises", description = "Ejecuta una consulta general para recuperar la lista completa de los paises disponibles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de paises recuperada con exito",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Result.class))),
        @ApiResponse(responseCode = "204", description = "Peticion exitosa, pero sin contenido", content = @Content),
        @ApiResponse(responseCode = "400", description = "Error en la peticion al intentar obtener los registros", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping()
    public ResponseEntity<Result> GetAll() {
        Result result = PaisService.getPaises();
        return new ResponseEntity<>(result, result.correct ? HttpStatus.OK : HttpStatus.BAD_REQUEST);

    }
}
