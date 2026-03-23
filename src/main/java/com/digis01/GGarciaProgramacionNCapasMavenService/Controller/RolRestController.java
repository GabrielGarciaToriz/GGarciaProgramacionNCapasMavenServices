package com.digis01.GGarciaProgramacionNCapasMavenService.Controller;

import com.digis01.GGarciaProgramacionNCapasMavenService.DTO.Result;
import com.digis01.GGarciaProgramacionNCapasMavenService.Service.CatalogoService;
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
@RequestMapping("api/rol")
@Tag(name = "Catalogo: Roles", description = "Endpoint para consultar los roles disponibles para los usuarios")
public class RolRestController {

    @Autowired
    private CatalogoService RolService;

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
        Result result = RolService.getRoles();
        return new ResponseEntity<>(result, result.correct ? HttpStatus.OK : HttpStatus.BAD_REQUEST);

    }
}
