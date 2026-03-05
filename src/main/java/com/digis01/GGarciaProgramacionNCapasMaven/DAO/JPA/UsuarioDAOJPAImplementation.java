package com.digis01.GGarciaProgramacionNCapasMaven.DAO.JPA;

import com.digis01.GGarciaProgramacionNCapasMaven.DAO.IUsuario;
import com.digis01.GGarciaProgramacionNCapasMaven.JPA.ColoniaJPA;
import com.digis01.GGarciaProgramacionNCapasMaven.JPA.DireccionJPA;
import com.digis01.GGarciaProgramacionNCapasMaven.JPA.RolJPA;
import com.digis01.GGarciaProgramacionNCapasMaven.JPA.UsuarioJPA;
import com.digis01.GGarciaProgramacionNCapasMaven.ML.Colonia;
import com.digis01.GGarciaProgramacionNCapasMaven.ML.Direccion;
import com.digis01.GGarciaProgramacionNCapasMaven.ML.Estado;
import com.digis01.GGarciaProgramacionNCapasMaven.ML.Municipio;
import com.digis01.GGarciaProgramacionNCapasMaven.ML.Pais;
import com.digis01.GGarciaProgramacionNCapasMaven.ML.Result;
import com.digis01.GGarciaProgramacionNCapasMaven.ML.Rol;
import com.digis01.GGarciaProgramacionNCapasMaven.ML.Usuario;
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
                         SELECT DISTINCT u FROM UsuarioJPA u
                         LEFT JOIN FETCH u.rol
                         LEFT JOIN FETCH u.direcciones d
                         LEFT JOIN FETCH d.colonia c
                         LEFT JOIN FETCH c.municipio m
                         LEFT JOIN FETCH m.estado e
                         LEFT JOIN FETCH e.pais
                         """;
            TypedQuery<UsuarioJPA> query = EntityManager.createQuery(jpql, UsuarioJPA.class);
            List<UsuarioJPA> usuariosJPA = query.getResultList();
            for (UsuarioJPA usuarioJPA : usuariosJPA) {
                Usuario usuarioML = mapearUusarioJPAtoML(usuarioJPA);
                result.objects.add(usuarioML);
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
    public Result GetAllById(int IdUsuario) {
        Result result = new Result();
        result.objects = new ArrayList<>();
        try {
            String jpql = """
                         SELECT DISTINCT u FROM UsuarioJPA u
                         LEFT JOIN FETCH u.rol
                         LEFT JOIN FETCH u.direcciones d
                         LEFT JOIN FETCH d.colonia c
                         LEFT JOIN FETCH c.municipio m
                         LEFT JOIN FETCH m.estado e
                         LEFT JOIN FETCH e.pais
                         WHERE u.idUsuario = :idUsuario 
                         """;
            TypedQuery<UsuarioJPA> query = EntityManager.createQuery(jpql, UsuarioJPA.class);
            query.setParameter("idUsuario", IdUsuario);
            List<UsuarioJPA> usuariosJPA = query.getResultList();
            for (UsuarioJPA usuarioJPA : usuariosJPA) {
                Usuario usuarioML = mapearUusarioJPAtoML(usuarioJPA);
                result.objects.add(usuarioML);
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
    public Result DeleteDireccionUsuariobyId(int IdUsuario) {
        Result result = new Result();

        try {
            UsuarioJPA usuarioJPA = EntityManager.find(UsuarioJPA.class, IdUsuario);
            if (usuarioJPA != null) {
                EntityManager.remove(usuarioJPA);
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
            DireccionJPA direccionJPA = EntityManager.find(DireccionJPA.class, IdDireccion);
            if (direccionJPA != null) {
                UsuarioJPA usuarioPrimario = direccionJPA.getUsuario();
                if (usuarioPrimario != null) {
                    usuarioPrimario.getDirecciones().remove(direccionJPA);
                }
                EntityManager.remove(direccionJPA);
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
            UsuarioJPA usuarioJPA = new UsuarioJPA();
            usuarioJPA.setNombre(usuario.getNombre());
            usuarioJPA.setApellidoPaterno(usuario.getApellidoPaterno());
            usuarioJPA.setApellidoMaterno(usuario.getApellidoMaterno());
            usuarioJPA.setFechaNacimiento(usuario.getFechaNacimiento());
            usuarioJPA.setCelular(usuario.getCelular());
            usuarioJPA.setCurp(usuario.getCurp());
            usuarioJPA.setUserName(usuario.getUserName());
            usuarioJPA.setEmail(usuario.getEmail());
            usuarioJPA.setPassword(usuario.getPassword());
            usuarioJPA.setSexo(usuario.getSexo());
            usuarioJPA.setTelefono(usuario.getTelefono());
            usuarioJPA.setEstatus(1);
            RolJPA rolJPA = EntityManager.getReference(RolJPA.class, usuario.getRol().getIdRol());
            usuarioJPA.setRol(rolJPA);
            List<DireccionJPA> direccionesJPA = new ArrayList<>();
            if (usuario.getDirecciones() != null && !usuario.getDirecciones().isEmpty()) {
                Direccion direccionML = usuario.getDirecciones().get(0);
                DireccionJPA direccionJPA = new DireccionJPA();

                direccionJPA.setCalle(direccionML.getCalle());
                direccionJPA.setNumeroExterior(direccionML.getNumeroExterior());
                direccionJPA.setNumeroInterior(direccionML.getNumeroInterior());
                ColoniaJPA coloniaJPA = EntityManager.getReference(ColoniaJPA.class, direccionML.getColonia().getIdColonia());
                direccionJPA.setColonia(coloniaJPA);

                direccionJPA.setUsuario(usuarioJPA);
                direccionesJPA.add(direccionJPA);
            }
            usuarioJPA.setDirecciones(direccionesJPA);
            EntityManager.persist(usuarioJPA);
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
            UsuarioJPA usuarioJPA = EntityManager.find(UsuarioJPA.class, usuario.getIdUsuario());
            if (usuarioJPA != null) {
                usuarioJPA.setNombre(usuario.getNombre());
                usuarioJPA.setApellidoPaterno(usuario.getApellidoPaterno());
                usuarioJPA.setApellidoMaterno(usuario.getApellidoMaterno());
                usuarioJPA.setCelular(usuario.getCelular());
                usuarioJPA.setCurp(usuario.getCurp());
                usuarioJPA.setUserName(usuario.getUserName());
                usuarioJPA.setEmail(usuario.getEmail());
                usuarioJPA.setPassword(usuario.getPassword());
                usuarioJPA.setSexo(usuario.getSexo());
                usuarioJPA.setTelefono(usuario.getTelefono());
                usuarioJPA.setFechaNacimiento(usuario.getFechaNacimiento());

                RolJPA rolJPA = EntityManager.getReference(RolJPA.class, usuario.getRol().getIdRol());
                usuarioJPA.setRol(rolJPA);
                EntityManager.merge(usuarioJPA);
                result.correct = true;

            } else {
                result.correct = false;
                result.errorMessage = "No se encontro el ID";
            }
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
            query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);
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

    private Usuario mapearUusarioJPAtoML(UsuarioJPA usuarioJPA) {
        Usuario usuarioML = new Usuario();
        usuarioML.setRol(new Rol());
        usuarioML.setDirecciones(new ArrayList<>());

        usuarioML.setIdUsuario(usuarioJPA.getIdUsuario());
        usuarioML.setNombre(usuarioJPA.getNombre());
        usuarioML.setApellidoPaterno(usuarioJPA.getApellidoPaterno());
        usuarioML.setApellidoMaterno(usuarioJPA.getApellidoMaterno());
        usuarioML.setFechaNacimiento(usuarioJPA.getFechaNacimiento());
        usuarioML.setCelular(usuarioJPA.getCelular());
        usuarioML.setCurp(usuarioJPA.getCurp());
        usuarioML.setUserName(usuarioJPA.getUserName());
        usuarioML.setEmail(usuarioJPA.getEmail());
        usuarioML.setPassword(usuarioJPA.getPassword());
        usuarioML.setSexo(usuarioJPA.getSexo());
        usuarioML.setTelefono(usuarioJPA.getTelefono());
        usuarioML.setEstatus(usuarioJPA.getEstatus());
        if (usuarioJPA.getRol() != null) {
            usuarioML.getRol().setIdRol(usuarioJPA.getRol().getIdRol());
            usuarioML.getRol().setNombre(usuarioJPA.getRol().getNombre());
        }
        if (usuarioJPA.getDirecciones() != null) {
            for (DireccionJPA direccionJPA : usuarioJPA.getDirecciones()) {
                Direccion direccionML = new Direccion();
                direccionML.setIdDireccion(direccionJPA.getIdDireccion());
                direccionML.setCalle(direccionJPA.getCalle());
                direccionML.setNumeroExterior(direccionJPA.getNumeroExterior());
                direccionML.setNumeroInterior(direccionJPA.getNumeroInterior());

                if (direccionJPA.getColonia() != null) {
                    direccionML.setColonia(new Colonia());
                    direccionML.getColonia().setIdColonia(direccionJPA.getColonia().getIdColonia());
                    direccionML.getColonia().setNombre(direccionJPA.getColonia().getNombre());
                    direccionML.getColonia().setCodigoPostal(direccionJPA.getColonia().getCodigoPostal());
                    if (direccionJPA.getColonia().getMunicipio() != null) {
                        direccionML.getColonia().setMunicipio(new Municipio());
                        direccionML.getColonia().getMunicipio().setIdMunicipio(direccionJPA.getColonia().getMunicipio().getIdMunicipio());
                        direccionML.getColonia().getMunicipio().setNombre(direccionJPA.getColonia().getMunicipio().getNombre());
                        if (direccionJPA.getColonia().getMunicipio().getEstado() != null) {
                            direccionML.getColonia().getMunicipio().setEstado(new Estado());
                            direccionML.getColonia().getMunicipio().getEstado().setIdEstado(direccionJPA.getColonia().getMunicipio().getEstado().getIdEstado());
                            direccionML.getColonia().getMunicipio().getEstado().setNombre(direccionJPA.getColonia().getMunicipio().getEstado().getNombre());
                            if (direccionJPA.getColonia().getMunicipio().getEstado().getPais() != null) {
                                direccionML.getColonia().getMunicipio().getEstado().setPais(new Pais());
                                direccionML.getColonia().getMunicipio().getEstado().getPais().setIdPais(direccionJPA.getColonia().getMunicipio().getEstado().getPais().getIdPais());
                                direccionML.getColonia().getMunicipio().getEstado().getPais().setNombre(direccionJPA.getColonia().getMunicipio().getEstado().getPais().getNombre());
                            }
                        }
                    }
                }
                usuarioML.getDirecciones().add(direccionML);
            }
        }
        return usuarioML;
    }

}
