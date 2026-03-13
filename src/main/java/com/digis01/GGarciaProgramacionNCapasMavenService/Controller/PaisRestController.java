package com.digis01.GGarciaProgramacionNCapasMavenService.Controller;

import com.digis01.GGarciaProgramacionNCapasMavenService.DAO.JPA.PaisDAOJPAImplementation;
import com.digis01.GGarciaProgramacionNCapasMavenService.Entity.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/pais")
@Tag(name = "Catalogo: Pais", description = "EndPoint para la consulta y gestion del catalogo de paises")
public class PaisRestController {

    @Autowired
    private PaisDAOJPAImplementation PaisDAOJPA;

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
    public ResponseEntity GetAll() {
        try {
            Result result = PaisDAOJPA.GetAll();
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
