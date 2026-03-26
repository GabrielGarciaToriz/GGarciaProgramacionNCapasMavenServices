package com.digis01.GGarciaProgramacionNCapasMavenService.service;

import com.digis01.GGarciaProgramacionNCapasMavenService.dto.Result;
import com.digis01.GGarciaProgramacionNCapasMavenService.entity.Direccion;
import com.digis01.GGarciaProgramacionNCapasMavenService.entity.Usuario;
import com.digis01.GGarciaProgramacionNCapasMavenService.repository.DireccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;

@Service
public class DireccionService {

    @Autowired
    private DireccionRepository direccionRepository;

    public Result getAllByIdUsuario(int idUsuario) {
        Result result = new Result();
        try {
            result.objects = new ArrayList<>(direccionRepository.findByUsuarioIdUsuario(idUsuario));
            result.correct = true;
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }

    @Transactional
    public Result addOrModifyDireccion(Direccion direccion, int idUsuario) {
        Result result = new Result();
        try {
            Usuario usuarioRef = new Usuario();
            usuarioRef.setIdUsuario(idUsuario); 
            direccion.setUsuario(usuarioRef);
            
            direccionRepository.save(direccion);
            result.correct = true;
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }

    @Transactional
    public Result deleteDireccion(int idDireccion) {
        Result result = new Result();
        try {
            direccionRepository.deleteById(idDireccion);
            result.correct = true;
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }
}