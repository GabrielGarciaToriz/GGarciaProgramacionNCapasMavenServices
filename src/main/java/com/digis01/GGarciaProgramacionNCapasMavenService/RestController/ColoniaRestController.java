package com.digis01.GGarciaProgramacionNCapasMavenService.RestController;

import com.digis01.GGarciaProgramacionNCapasMavenService.DAO.JPA.ColoniaDAOJPAImplementation;
import com.digis01.GGarciaProgramacionNCapasMavenService.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/colonia")
public class ColoniaRestController {

    @Autowired
    private ColoniaDAOJPAImplementation ColoniaDAOJPA;

    @GetMapping("/{idMunicipio}")
    public ResponseEntity GetById(@PathVariable("idMunicipio") int IdMunicipio) {
        try {
            Result result = ColoniaDAOJPA.GetAllById(IdMunicipio);
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

    @GetMapping("/codigoPostal/{codigoPostal}")
    public ResponseEntity GetByCodigoPostal(@PathVariable("codigoPostal") String CodigoPostal) {
        try {
            Result result = ColoniaDAOJPA.GetByCodigoPostal(CodigoPostal);
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
