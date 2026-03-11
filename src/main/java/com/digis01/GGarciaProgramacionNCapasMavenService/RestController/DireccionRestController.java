package com.digis01.GGarciaProgramacionNCapasMavenService.RestController;

import com.digis01.GGarciaProgramacionNCapasMavenService.DAO.JPA.DireccionDAOJPAImplementation;
import com.digis01.GGarciaProgramacionNCapasMavenService.JPA.Result;
import com.digis01.GGarciaProgramacionNCapasMavenService.JPA.Direccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/direccion")
public class DireccionRestController {
    
    @Autowired
    private DireccionDAOJPAImplementation DireccionDAOJPA;
    
    @GetMapping("/{idUsuario}")
    public ResponseEntity GetById(@PathVariable("idUsuario") int idUsuario) {
        try {
            Result result = DireccionDAOJPA.DireccionGetAllById(idUsuario);
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
    
    @PostMapping("/{idUsuario}")
    public ResponseEntity AddDireccionToUsuario(@PathVariable("idUsuario") int IdUsuario, @RequestBody Direccion direccion) {
        try {
            Result result = DireccionDAOJPA.DireccionAdd(direccion, IdUsuario);
            if (result.correct) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.badRequest().body(result.errorMessage);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e);
        }
    }
    
    @PutMapping("/{idDireccion}/{idUsuario}")
    public ResponseEntity ModifyDireccion(@PathVariable("idDireccion") int IdDireccion, @PathVariable("idUsuario") int IdUsuario, @RequestBody Direccion direccion) {
        try {
            direccion.setIdDireccion(IdDireccion);
            Result result = DireccionDAOJPA.DireccionModify(direccion, IdUsuario);
            if (result.correct) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.badRequest().body(result.errorMessage);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e);
        }
    }
    
    @DeleteMapping("/{idDireccion}")
    public ResponseEntity DeleteDireccion(@PathVariable("idDireccion") int idDireccion) {
        try {
            Result result = DireccionDAOJPA.DeleteDireccion(idDireccion);
            if (result.correct) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.badRequest().body(result.errorMessage);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getLocalizedMessage());
        }
    }
    
}
