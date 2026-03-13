package com.digis01.GGarciaProgramacionNCapasMavenService.RestController;

import com.digis01.GGarciaProgramacionNCapasMavenService.DAO.JPA.UsuarioDAOJPAImplementation;
import com.digis01.GGarciaProgramacionNCapasMavenService.JPA.Direccion;
import com.digis01.GGarciaProgramacionNCapasMavenService.JPA.Result;
import com.digis01.GGarciaProgramacionNCapasMavenService.JPA.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/usuario")
@Tag(name = "Usuarios", description = "Gestion completa de los usuarios (Alta, Baja, Modificacion, Busqueda y Cambio de estatus ")
public class UsuarioRestController {

    @Autowired
    private UsuarioDAOJPAImplementation usuarioDAOJPA;

    // <editor-fold defaultstate="collapsed" desc="--- GET MAPPINGS / LECTURA ---">
    @Operation(
            summary = "Obtener todos los usuarios",
            description = "Recupera la lista general de todos los usuarios registrados en el sistema."
    )
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Usuarios listados correctamente",
                        content = @Content(
                                schema = @Schema(implementation = Result.class)
                        )
                ),
                @ApiResponse(
                        responseCode = "204",
                        description = "No hay usuarios registrados",
                        content = @Content
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Error en la peticion",
                        content = @Content
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Error internod el servidor",
                        content = @Content
                )
            }
    )
    @GetMapping()
    public ResponseEntity GetAll() {
        try {
            Result result = usuarioDAOJPA.GetAll();
            if (result.correct) {
                if (result.objects != null && !result.objects.isEmpty()) {
                    return ResponseEntity.ok(result);
                } else {
                    return ResponseEntity.noContent().build();
                }
            } else {
                return ResponseEntity.badRequest().body(result.errorMessage);
            }
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(ex);
        }
    }

    @Operation(
            summary = "Obtener usuarios por ID",
            description = "Buscar un usuario especifico y decuelve su informacion detallada"
    )
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Usuario encontrado",
                        content = @Content(
                                schema = @Schema(
                                        implementation = Usuario.class
                                )
                        )
                ),
                @ApiResponse(
                        responseCode = "204",
                        description = "Usuario no encontrado",
                        content = @Content
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Error en la peticion",
                        content = @Content
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Error interno del servidor"
                ),}
    )

    @GetMapping("/{idUsuario}")
    public ResponseEntity GetAllById(
            @Parameter(description = "ID unico del usuario", example = "65")
            @PathVariable("idUsuario") int IdUsuario) {
        try {
            Result result = usuarioDAOJPA.GetAllById(IdUsuario);
            if (result.correct) {
                if (result.objects != null && !result.objects.isEmpty()) {
                    return ResponseEntity.ok(result.objects.get(0));
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

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="--- POST MAPPINGS / ENVIO ---">
    @Operation(
            summary = "Agregar usuario y direccion",
            description = "Crea un nuevo usuario en la base de datos, incluye una direccion para crearlas en la misma transaccion"
    )
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Usuario y direccion agregados correctamente",
                        content = @Content(
                                schema = @Schema(implementation = Result.class)
                        )
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Fallo al insertar el usuario o direccion",
                        content = @Content
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Fallo interno del servidor",
                        content = @Content
                )
            })
    @PostMapping()
    public ResponseEntity UsuarioDireccionAdd(@RequestBody Usuario usuario) {
        try {
            if (usuario.getDirecciones() != null) {
                for (Direccion direccion : usuario.getDirecciones()) {
                    direccion.setUsuario(usuario);
                }
            }
            Result result = usuarioDAOJPA.AddUsuarioDireccion(usuario);
            if (result.correct) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.badRequest().body(result.errorMessage);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getLocalizedMessage());
        }

    }

    @Operation(
            summary = "Busqueda avanzada de usuario",
            description = "Reliza una busqeuda de usuarios filtrando por las propiedades enviadas en el cuerpo de la peticion"
    )

    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Resultados de la busqueda encontrados",
                        content = @Content(
                                schema = @Schema(implementation = Usuario.class))
                ),
                @ApiResponse(
                        responseCode = "204",
                        description = "No se encontraron resultados para la busqueda",
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
                        content = @Content
                )
            }
    )

    @PostMapping("/buscar")
    public ResponseEntity BusquedaUsuarioDireccion(@RequestBody Usuario usuario) {
        try {
            Result result = usuarioDAOJPA.UsuarioDireccionBusqueda(usuario);
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
            summary = "Cambiar estatus del Usuario",
            description = "Modifica rapidamente el estaus (activo/inactivo) de un usuario sin alterar sus otros datos"
    )
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Estatus cambiado correctamente",
                        content = @Content(
                                schema = @Schema(implementation = Result.class)
                        )
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "No se pudo cambiar el estatus",
                        content = @Content
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Error interno del servidor",
                        content = @Content
                )
            }
    )
    @PostMapping("/cambioStatus/{idUsuario}/{estatus}")
    public ResponseEntity CambiarEstatus(
            @Parameter(description = "ID del usuario", example = "102")
            @PathVariable("idUsuario") int idUsuario,
            @Parameter(description = "Nuevo a estatus a aplicar (ej. 1 para activo, 0 para inactivo", example = "0")
            @PathVariable("estatus") int Estatus) {
        try {
            Result result = usuarioDAOJPA.CambiarEstatus(idUsuario, Estatus);
            if (result.correct) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.badRequest().body(result.errorMessage);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getLocalizedMessage());
        }
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="--- DELETE MAPPINGS / LECTURA ---">
    @Operation(
            summary = "Eliminar usuarios y sus direccines",
            description = "Elimina a un uusario de la base de datos y en cascada todas sus direcciones asociadas"
    )
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Usuario eliminado junto a sus direcciones asosciadas",
                        content = @Content(
                                schema = @Schema(implementation = Result.class))
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Error al intenrar eliminar al usuario y sus direcciones asociadas",
                        content = @Content
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Error interno del servidor",
                        content = @Content
                )
            })
    @DeleteMapping("/{idUsuario}")
    public ResponseEntity DeleteUsuarioDireccion(
            @Parameter(description = "Id del usuario a eliminar", example = "102")
            @PathVariable("idUsuario") int idUsuario) {
        try {
            Result result = usuarioDAOJPA.DeleteDireccionUsuariobyId(idUsuario);
            if (result.correct) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.badRequest().body(result.errorMessage);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e);
        }
    }
    // </editor-fold>
}
