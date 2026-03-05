package com.digis01.GGarciaProgramacionNCapasMaven.RestController;

import com.digis01.GGarciaProgramacionNCapasMaven.DAO.JPA.UsuarioDAOJPAImplementation;
import com.digis01.GGarciaProgramacionNCapasMaven.ML.Result;
import com.digis01.GGarciaProgramacionNCapasMaven.ML.Usuario;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/demo")
public class DemoRestController {

    @Autowired
    private UsuarioDAOJPAImplementation UsuarioDAOJPAImplementation;

    @GetMapping
    public String HolaMundo() {
        return "HolaMundo";
    }

    @GetMapping("calculadora/{Numero1}/{Numero2}")
    public int Calculadora(@PathVariable("Numero1") int Numero1, @PathVariable("Numero2") int Numero2) {
        return Numero1 + Numero2;
    }

    @PostMapping("calculadoraN")
    public int calculadoraN(@RequestBody List<Integer> numeros) {
        int suma = 0;
        for (int numero : numeros) {
            suma += numero;
        }
        return suma;
    }

    @GetMapping("usuarios")
    public ResponseEntity<List<Usuario>> GetAll() {
        Result result = UsuarioDAOJPAImplementation.GetAll();
        if (result.correct) {
            List<Usuario> listaUsuario = (List<Usuario>) (List<?>) result.objects;
            if (listaUsuario == null || listaUsuario.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(listaUsuario);
        } else {
            return ResponseEntity.status(HttpStatusCode.valueOf(500)).build();
        }
    }
}
