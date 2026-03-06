package com.digis01.GGarciaProgramacionNCapasMaven.RestController;

import com.digis01.GGarciaProgramacionNCapasMaven.DAO.JPA.PaisDAOJPAImplementation;
import com.digis01.GGarciaProgramacionNCapasMaven.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/pais")
public class PaisRestController {

    @Autowired
    private PaisDAOJPAImplementation PaisDAOJPA;

    @GetMapping()
    public ResponseEntity GetAll() {
        try {
            Result result = PaisDAOJPA.GetAll();
            if (result.correct) {
                if (result.objects != null && !result.objects.isEmpty()) {
                    return ResponseEntity.ok(result.objects);
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
