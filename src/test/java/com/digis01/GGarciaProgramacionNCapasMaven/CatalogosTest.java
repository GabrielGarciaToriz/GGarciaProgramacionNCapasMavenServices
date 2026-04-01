package com.digis01.GGarciaProgramacionNCapasMaven;

import com.digis01.GGarciaProgramacionNCapasMavenService.dto.Result;
import com.digis01.GGarciaProgramacionNCapasMavenService.service.CatalogoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = com.digis01.GGarciaProgramacionNCapasMavenService.GGarciaProgramacionNCapasMavenServiceApplication.class)
public class CatalogosTest {
    
    @Autowired
    private CatalogoService catalogoService;
    
    @Test
    public void Roles() {
        Result result = catalogoService.getRoles();
        Assertions.assertTrue(result.correct);
        Assertions.assertNotNull(result.objects);
    }
    
    @Test
    public void Pais() {
        Result result = catalogoService.getPaises();
        Assertions.assertTrue(result.correct);
        Assertions.assertNotNull(result.objects);
    }
    
    @Test
    public void EstadoByPais() {
        Result result = catalogoService.getEstadosByPais(1);
        Assertions.assertTrue(result.correct);
        Assertions.assertNotNull(result.objects);
    }
    
    @Test
    public void MunicipioByEstado() {
        Result result = catalogoService.getMunicipioByEstado(1);
        Assertions.assertTrue(result.correct);
        Assertions.assertNotNull(result.objects);
    }

    @Test
    public void ColoniaByMunicipio() {
        Result result = catalogoService.getColoniaByMunicipio(1);
        Assertions.assertTrue(result.correct);
        Assertions.assertNotNull(result.objects);
    }
    
}
