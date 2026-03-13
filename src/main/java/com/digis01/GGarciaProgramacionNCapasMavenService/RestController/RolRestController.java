package com.digis01.GGarciaProgramacionNCapasMavenService.RestController;

import com.digis01.GGarciaProgramacionNCapasMavenService.DAO.JPA.RolDAOJPAImplementation;
import com.digis01.GGarciaProgramacionNCapasMavenService.JPA.Result;
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
@RequestMapping("api/rol")
@Tag(name = "Catalogo: Roles", description = "Endpoint para consultar los roles disponibles para los usuarios")
public class RolRestController {

    @Autowired
    private RolDAOJPAImplementation RolDAOJPA;

    @Operation(
            summary = "Obtener todos los roles",
            description = "Recuperar la lista completa de roles del sistema ")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Roles recuperados con exito",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = Result.class)
                        )),
                @ApiResponse(
                        responseCode = "204",
                        description = "No hay roles registrados",
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
    @GetMapping
    public ResponseEntity GetAll() {
        try {
            Result result = RolDAOJPA.GetAll();
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
