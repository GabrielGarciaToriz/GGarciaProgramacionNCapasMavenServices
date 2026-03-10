package com.digis01.GGarciaProgramacionNCapasMavenService.DAO.JPA;

import com.digis01.GGarciaProgramacionNCapasMavenService.DAO.IUsuario;
import com.digis01.GGarciaProgramacionNCapasMavenService.JPA.Colonia;
import com.digis01.GGarciaProgramacionNCapasMavenService.JPA.Direccion;
import com.digis01.GGarciaProgramacionNCapasMavenService.JPA.Estado;
import com.digis01.GGarciaProgramacionNCapasMavenService.JPA.Municipio;
import com.digis01.GGarciaProgramacionNCapasMavenService.JPA.Pais;
import com.digis01.GGarciaProgramacionNCapasMavenService.JPA.Result;
import com.digis01.GGarciaProgramacionNCapasMavenService.JPA.Rol;
import com.digis01.GGarciaProgramacionNCapasMavenService.JPA.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("UsuarioDAOJPA")
public class UsuarioDAOJPAImplementation implements IUsuario {

    @Autowired
    private EntityManager EntityManager;

    @Override
    public Result GetAll() {
        Result result = new Result();
        result.objects = new ArrayList<>();
        try {
            String jpql = """
                         SELECT DISTINCT u FROM Usuario u
                         LEFT JOIN FETCH u.rol
                         LEFT JOIN FETCH u.direcciones d
                         LEFT JOIN FETCH d.colonia c
                         LEFT JOIN FETCH c.municipio m
                         LEFT JOIN FETCH m.estado e
                         LEFT JOIN FETCH e.pais
                         """;
            TypedQuery<Usuario> query = EntityManager.createQuery(jpql, Usuario.class);
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
    public Result GetAllById(int IdUsuario) {
        Result result = new Result();
        result.objects = new ArrayList<>();
        try {
            String jpql = """
                         SELECT DISTINCT u FROM Usuario u
                         LEFT JOIN FETCH u.rol
                         LEFT JOIN FETCH u.direcciones d
                         LEFT JOIN FETCH d.colonia c
                         LEFT JOIN FETCH c.municipio m
                         LEFT JOIN FETCH m.estado e
                         LEFT JOIN FETCH e.pais
                         WHERE u.idUsuario = :idUsuario 
                         """;
            TypedQuery<Usuario> query = EntityManager.createQuery(jpql, Usuario.class);
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
    public Result DeleteDireccionUsuariobyId(int IdUsuario) {
        Result result = new Result();

        try {
            Usuario usuario = EntityManager.find(Usuario.class, IdUsuario);
            if (usuario != null) {
                EntityManager.remove(usuario);
                result.correct = true;

            } else {
                result.correct = false;
                result.errorMessage = "No se pudo eliminar al usuario";
            }
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;

        }
        return result;
    }

    @Override
    @Transactional
    public Result DeleteDireccionById(int IdDireccion) {
        Result result = new Result();
        try {
            Direccion direccion = EntityManager.find(Direccion.class, IdDireccion);
            if (direccion != null) {
                Usuario usuarioPrimario = direccion.getUsuario();
                if (usuarioPrimario != null) {
                    usuarioPrimario.getDirecciones().remove(direccion);
                }
                EntityManager.remove(direccion);
                result.correct = true;
            } else {
                result.correct = false;
                result.errorMessage = "No se pudo borrar la direccion";
            }
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }

    @Override
    @Transactional
    public Result Add(Usuario usuario) {
        Result result = new Result();
        try {
            EntityManager.persist(usuario);
            result.correct = true;
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }

    @Override
    public Result UsuarioDireccionBusqueda(Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    @Transactional
    public Result ModifyUsuario(Usuario usuario) {
        Result result = new Result();
        try {
            EntityManager.merge(usuario);
            result.correct = true;

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }

    @Override
    public Result CambiarEstatus(int IdUsuario, int Estatus) {
        Result result = new Result();
        try {
            StoredProcedureQuery query = EntityManager.createStoredProcedureQuery("cambiarestatussp");
            query
                    .registerStoredProcedureParameter(1, Integer.class,
                            ParameterMode.IN);
            query
                    .registerStoredProcedureParameter(2, Integer.class,
                            ParameterMode.IN);
            query.setParameter(1, IdUsuario);
            query.setParameter(2, Estatus);
            query.execute();
            result.correct = true;
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }

    @Override
    public Result AddAll(List<Usuario> usuarios) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
