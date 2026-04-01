package com.digis01.GGarciaProgramacionNCapasMaven;

import com.digis01.GGarciaProgramacionNCapasMavenService.dto.Result;
import com.digis01.GGarciaProgramacionNCapasMavenService.service.DireccionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = com.digis01.GGarciaProgramacionNCapasMavenService.GGarciaProgramacionNCapasMavenServiceApplication.class)
public class DireccionTest {

    @Autowired
    private DireccionService direccionService;

    @Test
    public void GetAllDirecciones() {
        Result result = direccionService.getAllByIdUsuario(201);
        Assertions.assertTrue(result.correct);
        Assertions.assertNotNull(result.objects);
    }
    

}
