package com.digis01.GGarciaProgramacionNCapasMavenService.DAO.JPA;

import com.digis01.GGarciaProgramacionNCapasMavenService.DAO.IDireccion;
import com.digis01.GGarciaProgramacionNCapasMavenService.Entity.Usuario;
import com.digis01.GGarciaProgramacionNCapasMavenService.Entity.Direccion;
import com.digis01.GGarciaProgramacionNCapasMavenService.Entity.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DireccionDAOJPAImplementation implements IDireccion {

    @Autowired
    private EntityManager EntityManager;

    @Override
    public Result DireccionGetAllById(int IdUsuario) {
        Result result = new Result();
        try {
            String jpql = "SELECT d FROM Direccion d WHERE d.usuario.idUsuario = :idUsuario";
            TypedQuery<Direccion> query = EntityManager.createQuery(jpql, Direccion.class);
            query.setParameter("idUsuario", IdUsuario);
            result.objects = new ArrayList<>(query.getResultList());
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

            Usuario usuario = EntityManager.getReference(Usuario.class, IdUsuario);
            direccion.setUsuario(usuario);
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
    @Transactional
    public Result DireccionModify(Direccion direccion, int IdUsuario) {
        Result result = new Result();

        try {
            if (IdUsuario > 0) {
                Usuario usuario = EntityManager.getReference(Usuario.class, IdUsuario);
                direccion.setUsuario(usuario);
            }
            EntityManager.merge(direccion);
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
    public Result DeleteDireccion(int idDireccion) {

        Result result = new Result();
        try {
            String jpql = "DELETE FROM Direccion d WHERE d.idDireccion = :idDireccion";
            Query query = EntityManager.createQuery(jpql);
            query.setParameter("idDireccion", idDireccion);
            int realizado = query.executeUpdate();
            if (realizado > 0) {
                result.correct = true;
            }
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }
}
