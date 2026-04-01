package com.digis01.GGarciaProgramacionNCapasMaven;

import com.digis01.GGarciaProgramacionNCapasMavenService.dto.Result;
import com.digis01.GGarciaProgramacionNCapasMavenService.entity.Colonia;
import com.digis01.GGarciaProgramacionNCapasMavenService.entity.Direccion;
import com.digis01.GGarciaProgramacionNCapasMavenService.entity.Rol;
import com.digis01.GGarciaProgramacionNCapasMavenService.entity.Usuario;
import com.digis01.GGarciaProgramacionNCapasMavenService.service.UsuarioService;
import java.util.ArrayList;
import java.util.Date;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = com.digis01.GGarciaProgramacionNCapasMavenService.GGarciaProgramacionNCapasMavenServiceApplication.class)
public class UsuarioTest {

    @Autowired
    public UsuarioService usuarioService;

    @Test
    public void GetAll() {
        Result result = usuarioService.getAll();
        Assertions.assertTrue(result.correct);
        Assertions.assertNotNull(result.objects);
    }
    @Test
    public void GetById(){
        Result result = usuarioService.getAllById(201);
        Assertions.assertTrue(result.correct);
        Assertions.assertNotNull(result.objects);
    }

    @Test
    public void Add() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Gabriel");
        usuario.setApellidoPaterno("Garcia");
        usuario.setApellidoMaterno("Toriz");
        usuario.setFechaNacimiento(new Date());
        usuario.setCelular("5566889933");
        usuario.setCurp("GATG011227HDFRRBA0");
        usuario.setUserName("GabTraxTech");
        usuario.setEmail("gabtraxtexh@gmail.com");
        usuario.setPassword("$2a$12$VMfJ38satHAEwUnoEV50MusqYRV5K1n1JBSV.iWf5fagVJ/ywXir2");
        usuario.setSexo("M");
        usuario.setTelefono("5668993322");
        usuario.setEstatus(1);
        usuario.rol = new Rol();
        usuario.rol.setIdRol(1);
        Direccion direccion = new Direccion();
        direccion.setCalle("Rio Blanco");
        direccion.setNumeroExterior("212");
        direccion.setNumeroInterior("-");
        direccion.colonia = new Colonia();
        direccion.colonia.setIdColonia(10);
        direccion.setUsuario(usuario);
        usuario.direcciones = new ArrayList<>();
        usuario.direcciones.add(direccion);
        Result result = usuarioService.addOrModifyUsuario(usuario);
        Assertions.assertTrue(result.correct);
    }

}
