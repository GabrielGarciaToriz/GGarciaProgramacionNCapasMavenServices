package com.digis01.GGarciaProgramacionNCapasMavenService.DAO.JPA;

import com.digis01.GGarciaProgramacionNCapasMavenService.DAO.IUsuario;
import com.digis01.GGarciaProgramacionNCapasMavenService.JPA.Result;
import com.digis01.GGarciaProgramacionNCapasMavenService.JPA.Usuario;
import com.digis01.GGarciaProgramacionNCapasMavenService.JPA.UsuarioVista;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
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
                         ORDER BY u.apellidoPaterno ASC, u.nombre ASC
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
                         SELECT u FROM Usuario u
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
    @Transactional
    public Result DeleteDireccionUsuariobyId(int IdUsuario) {
        Result result = new Result();
        try {
            String eliminarDireccion = "DELETE FROM Direccion d WHERE d.usuario.idUsuario = :idUsuario";
            Query queryDireccion = EntityManager.createQuery(eliminarDireccion);
            queryDireccion.setParameter("idUsuario", IdUsuario);
            int direccionEliminada = queryDireccion.executeUpdate();
            if (direccionEliminada > 0) {
                String eliminarUsuario = "DELETE FROM Usuario WHERE idUsuario = :idUsuario";
                Query queryUsuario = EntityManager.createQuery(eliminarUsuario);
                queryUsuario.setParameter("idUsuario", IdUsuario);
                int usuarioEliminado = queryUsuario.executeUpdate();
                if (usuarioEliminado > 0) {
                    result.correct = true;
                } else {
                    result.correct = false;
                    result.errorMessage = "Fallo al elminar el usuario";
                }
            } else {
                result.correct = false;
                result.errorMessage = "Fallo al eliminar la o las direcciones";
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
    public Result AddUsuarioDireccion(Usuario usuario) {
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
    public Result UsuarioDireccionBusqueda(Usuario usuario) {
        Result result = new Result();
        try {
            String jpql = """
                         SELECT v FROM UsuarioVista v
                         WHERE (:nombre IS NULL OR LOWER (v.nombre) LIKE LOWER(CONCAT('%', :nombre ,'%')))
                         AND (:apellidoPaterno IS NULL OR LOWER (v.apellidoPaterno) LIKE LOWER (CONCAT('%', :apellidoPaterno, '%')))
                         AND (:apellidoMaterno IS NULL OR LOWER (v.apellidoMaterno) LIKE LOWER (CONCAT('%', :apellidoMaterno, '%')))
                         AND (:idRol = 0 OR v.idRol = :idRol)
                         """;
            TypedQuery<UsuarioVista> query = EntityManager.createQuery(jpql, UsuarioVista.class);
            query.setParameter("nombre", (usuario.getNombre() != null && !usuario.getNombre().isBlank() ? usuario.getNombre().trim() : ""));
            query.setParameter("apellidoPaterno", (usuario.getApellidoPaterno() != null && !usuario.getApellidoPaterno().isBlank() ? usuario.getApellidoPaterno() : ""));
            query.setParameter("apellidoMaterno", (usuario.getApellidoMaterno() != null && !usuario.getApellidoMaterno().isBlank() ? usuario.getApellidoMaterno().trim() : ""));
            int idRol = (usuario.getRol() != null ? usuario.getRol().getIdRol() : 0);
            query.setParameter("idRol", idRol);
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
    @Transactional
    public Result CambiarEstatus(int IdUsuario, int Estatus) {
        Result result = new Result();
        try {
            String jpql = "UPDATE Usuario u SET u.estatus = :estatus WHERE u.idUsuario = :idUsuario";
            int actualizacion = EntityManager.createQuery(jpql)
                    .setParameter("idUsuario", IdUsuario)
                    .setParameter("estatus", Estatus)
                    .executeUpdate();

            if (actualizacion > 0) {
                result.correct = true;
            } else {
                result.correct = false;
                result.errorMessage = "No se pudo modificar el usuario " + IdUsuario;
            }

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
