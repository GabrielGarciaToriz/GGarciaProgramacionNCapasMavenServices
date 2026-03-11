package com.digis01.GGarciaProgramacionNCapasMavenService.RestController;

import com.digis01.GGarciaProgramacionNCapasMavenService.DAO.JPA.UsuarioDAOJPAImplementation;
import com.digis01.GGarciaProgramacionNCapasMavenService.JPA.Direccion;
import com.digis01.GGarciaProgramacionNCapasMavenService.JPA.Result;
import com.digis01.GGarciaProgramacionNCapasMavenService.JPA.Usuario;
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
public class UsuarioRestController {

    @Autowired
    private UsuarioDAOJPAImplementation usuarioDAOJPA;

    // <editor-fold defaultstate="collapsed" desc="--- GET MAPPINGS / LECTURA ---">
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

    @GetMapping("/{idUsuario}")
    public ResponseEntity GetAllById(@PathVariable("idUsuario") int IdUsuario) {
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

    @PostMapping("/cambioStatus/{idUsuario}/{estatus}")
    public ResponseEntity CambiarEstatus(@PathVariable("idUsuario") int idUsuario, @PathVariable("estatus") int Estatus) {
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
    @DeleteMapping("/{idUsuario}")
    public ResponseEntity DeleteUsuarioDireccion(@PathVariable("idUsuario") int idUsuario) {
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
