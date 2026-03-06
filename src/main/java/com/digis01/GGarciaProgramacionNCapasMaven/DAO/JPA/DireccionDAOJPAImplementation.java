package com.digis01.GGarciaProgramacionNCapasMaven.DAO.JPA;

import com.digis01.GGarciaProgramacionNCapasMaven.DAO.IDireccion;
import com.digis01.GGarciaProgramacionNCapasMaven.JPA.Usuario;
import com.digis01.GGarciaProgramacionNCapasMaven.JPA.Direccion;
import com.digis01.GGarciaProgramacionNCapasMaven.JPA.Result;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DireccionDAOJPAImplementation implements IDireccion {

    @Autowired
    private EntityManager EntityManager;

    @Override
    public Result DireccionGetAllById(int IdDireccion) {
        Result result = new Result();
        result.objects = new ArrayList();
        try {
            Direccion direccion = EntityManager.find(Direccion.class, IdDireccion);
            if (direccion != null) {
                result.objects.add(direccion);
            }
            result.correct = true;
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }

    @Override
    @Transactional
    public Result DireccionAdd(Direccion direccion, int IdUsuario) {
        Result result = new Result();
        try {
            if (IdUsuario > 0) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(IdUsuario);;
                direccion.setUsuario(usuario);
            }
            EntityManager.persist(direccion);
            result.correct = true;
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }

    @Override
    public Result DireccionModify(Direccion direccion, int IdUsuario) {
        Result result = new Result();

        try {
            if (IdUsuario > 0) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(IdUsuario);
                direccion.setUsuario(usuario);
            }
            EntityManager.merge(direccion);
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }
}
