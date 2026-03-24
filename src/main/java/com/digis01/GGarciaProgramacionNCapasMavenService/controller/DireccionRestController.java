package com.digis01.GGarciaProgramacionNCapasMavenService.controller;

import com.digis01.GGarciaProgramacionNCapasMavenService.dto.Result;
import com.digis01.GGarciaProgramacionNCapasMavenService.entity.Direccion;
import com.digis01.GGarciaProgramacionNCapasMavenService.service.DireccionService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.digis01.GGarciaProgramacionNCapasMavenService.service.UsuarioService;

@RestController
@RequestMapping("api/direccion")
@Tag(name = "Direcciones", description = "Endpoints para crear, leer, actualizar y eliminar (CRUD( las direcciones asociadas a un usuario")
public class DireccionRestController {

    @Autowired
    private DireccionService direccionService;

    @Autowired
    private UsuarioService usuarioService;

    // ===== ENDPOINTS DE AUTOSERVICIO (Usuario accede solo sus propias direcciones) =====
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Result> GetMisDirecciones(Authentication authentication) {
        Integer idUsuario = obtenerIdUsuarioAutenticado(authentication);
        if (idUsuario == null) {
            return new ResponseEntity<>(crearRespuestaNoAutorizado(), HttpStatus.UNAUTHORIZED);
        }

        Result result = direccionService.getAllByIdUsuario(idUsuario);
        return new ResponseEntity<>(result, result.correct ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Result> AddMiDireccion(Authentication authentication, @RequestBody Direccion direccion) {
        Integer idUsuario = obtenerIdUsuarioAutenticado(authentication);
        if (idUsuario == null) {
            return new ResponseEntity<>(crearRespuestaNoAutorizado(), HttpStatus.UNAUTHORIZED);
        }

        Result result = direccionService.addOrModifyDireccion(direccion, idUsuario);
        return new ResponseEntity<>(result, result.correct ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/me/{idDireccion}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Result> ModifyMiDireccion(
            @PathVariable("idDireccion") int idDireccion,
            Authentication authentication,
            @RequestBody Direccion direccion) {
        Integer idUsuario = obtenerIdUsuarioAutenticado(authentication);
        if (idUsuario == null) {
            return new ResponseEntity<>(crearRespuestaNoAutorizado(), HttpStatus.UNAUTHORIZED);
        }

        Result result = direccionService.modifyDireccionByUsuario(idDireccion, direccion, idUsuario);
        return new ResponseEntity<>(result, result.correct ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/me/{idDireccion}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Result> DeleteMiDireccion(
            @PathVariable("idDireccion") int idDireccion,
            Authentication authentication) {
        Integer idUsuario = obtenerIdUsuarioAutenticado(authentication);
        if (idUsuario == null) {
            return new ResponseEntity<>(crearRespuestaNoAutorizado(), HttpStatus.UNAUTHORIZED);
        }

        Result result = direccionService.deleteDireccionByUsuario(idDireccion, idUsuario);
        return new ResponseEntity<>(result, result.correct ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    // ===== ENDPOINTS ADMINISTRATIVOS (Solo Administrador) =====
    @Operation(summary = "Obtener direcciones por Usuario",
            description = "Recupera todas las direcciones que pertenecen a un usuario especifico mediante su ID")
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "Direcciones recuperadas con exito",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = Result.class)
                )),
        @ApiResponse(
                responseCode = "204",
                description = "El usuario no tiene direcciones registradas",
                content = @Content
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Error en la peticion",
                content = @Content
        ),
        @ApiResponse(
                responseCode = "500",
                description = "Error interno del servidor",
                content = @Content)
    })

    @GetMapping("/{idUsuario}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<Result> GetById(
            @Parameter(
                    description = "ID del usuario",
                    example = "65")
            @PathVariable("idUsuario") int idUsuario) {
        Result result = direccionService.getAllByIdUsuario(idUsuario);
        return new ResponseEntity<>(result, result.correct ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @Operation(
            summary = "Agregar nueva direccion",
            description = "Añade una nueva direccion y la asocia a un usuario existente")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Direccion guardad con exito ",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = Result.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "Error en la validacion o en la creacion",
                        content = @Content),
                @ApiResponse(
                        responseCode = "500",
                        description = "Error internod el servidor",
                        content = @Content
                )
            })

    @PostMapping("/{idUsuario}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity <Result>AddDireccionToUsuario(@PathVariable("idUsuario") int IdUsuario, @RequestBody Direccion direccion) {
        Result result = direccionService.addOrModifyDireccion(direccion, IdUsuario);
        return new ResponseEntity<>(result, result.correct ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @Operation(
            summary = "Modificar direccion existente",
            description = "Acrtualiza los datos de una direccion basandos en su ID y el ID del usuario")
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "Direccion actualizada correctamente",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(
                                implementation = Result.class))
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Error al intentar actualizar",
                content = @Content
        ),
        @ApiResponse(
                responseCode = "500",
                description = "Error interno del servidor",
                content = @Content)
    })

    @PutMapping("/{idDireccion}/{idUsuario}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity <Result>ModifyDireccion(
            @Parameter(description = "ID de la direccion a modificar", example = "121")
            @PathVariable("idDireccion") int IdDireccion,
            @Parameter(description = "ID del usuario propietario de la direccion", example = "65")
            @PathVariable("idUsuario") int IdUsuario,
            @RequestBody Direccion direccion) {
        Result result = direccionService.modifyDireccionByUsuario(IdDireccion, direccion, IdUsuario);
        return new ResponseEntity<>(result, result.correct ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @Operation(
            summary = "Eliminar direccion",
            description = "Eliminar fisicamente o logicamente una direccion segun su ID")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Direccion eliminada correctamente",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(
                                        implementation = Result.class)
                        )),
                @ApiResponse(
                        responseCode = "400",
                        description = "Error el eliminar la direccion",
                        content = @Content
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Error interno del servidor",
                        content = @Content
                )
            })

    @DeleteMapping("/{idDireccion}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<Result> DeleteDireccion(
            @Parameter(
                    description = "ID de la direccion a eliminar",
                    example = "121")
            @PathVariable("idDireccion") int idDireccion) {
        Result result = direccionService.deleteDireccion(idDireccion);
        return new ResponseEntity<>(result, result.correct ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    private Integer obtenerIdUsuarioAutenticado(Authentication authentication) {
        if (authentication == null) {
            return null;
        }
        return usuarioService.obtenerIdPorUserName(authentication.getName());
    }

    private Result crearRespuestaNoAutorizado() {
        Result result = new Result();
        result.correct = false;
        result.errorMessage = "No se pudo identificar al usuario autenticado.";
        return result;
    }

}
