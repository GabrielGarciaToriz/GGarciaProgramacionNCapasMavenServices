package com.digis01.GGarciaProgramacionNCapasMaven.RestController;

import com.digis01.GGarciaProgramacionNCapasMaven.DAO.JPA.MunicipioDAOJPAImplementation;
import com.digis01.GGarciaProgramacionNCapasMaven.JPA.Result;
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
    public ResponseEntity GetAllById(@PathVariable("idEstado") int idEstado){
        try {
            Result result = MunicipioDAOJPA.GetAllById(idEstado);
            if(result.correct){
                
            }
        } catch (Exception e) {
        }
        return null;
    }
    
}
