package com.digis01.GGarciaProgramacionNCapasMavenService.RestController;

import com.digis01.GGarciaProgramacionNCapasMavenService.DAO.JPA.MunicipioDAOJPAImplementation;
import com.digis01.GGarciaProgramacionNCapasMavenService.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/municipio")
public class MunicipioRestController {

    @Autowired
    private MunicipioDAOJPAImplementation MunicipioDAOJPA;

    @GetMapping("/{idEstado}")
    public ResponseEntity GetAllById(@PathVariable("idEstado") int idEstado) {
        try {
            Result result = MunicipioDAOJPA.GetAllById(idEstado);
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
