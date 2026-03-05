package com.digis01.GGarciaProgramacionNCapasMaven.DAO.JPA;

import com.digis01.GGarciaProgramacionNCapasMaven.DAO.IDireccion;
import com.digis01.GGarciaProgramacionNCapasMaven.JPA.ColoniaJPA;
import com.digis01.GGarciaProgramacionNCapasMaven.JPA.DireccionJPA;
import com.digis01.GGarciaProgramacionNCapasMaven.JPA.UsuarioJPA;
import com.digis01.GGarciaProgramacionNCapasMaven.ML.Colonia;
import com.digis01.GGarciaProgramacionNCapasMaven.ML.Direccion;
import com.digis01.GGarciaProgramacionNCapasMaven.ML.Result;
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
            DireccionJPA direccionJPA = EntityManager.find(DireccionJPA.class, IdDireccion);
            if (direccionJPA != null) {
                Direccion direccionML = JPAtoML(direccionJPA);
                result.objects.add(direccionML);
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
            DireccionJPA direccionJPA = MLtoJPA(direccion, IdUsuario);
            EntityManager.persist(direccionJPA);
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
            DireccionJPA direccionJPA = MLtoJPA(direccion, IdUsuario);
            EntityManager.merge(direccionJPA);
            result.correct = true;
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }

    private Direccion JPAtoML(DireccionJPA direccionJPA) {
        Direccion direccionML = new Direccion();
        direccionML.setIdDireccion(direccionJPA.getIdDireccion());
        direccionML.setCalle(direccionJPA.getCalle());
        direccionML.setNumeroExterior(direccionJPA.getNumeroExterior());
        direccionML.setNumeroInterior(direccionJPA.getNumeroInterior());
        if (direccionJPA.getColonia() != null) {
            Colonia coloniaML = new Colonia();
            coloniaML.setIdColonia(direccionJPA.getColonia().getIdColonia());
            direccionML.setColonia(coloniaML);
        }

        return direccionML;
    }

    private DireccionJPA MLtoJPA(Direccion direccionML, int IdUsuario) {
        DireccionJPA direccionJPA = new DireccionJPA();

        direccionJPA.setCalle(direccionML.getCalle());
        direccionJPA.setNumeroExterior(direccionML.getNumeroExterior());
        direccionJPA.setNumeroInterior(direccionML.getNumeroInterior());

        if (direccionML.getIdDireccion() > 0) {
            direccionJPA.setIdDireccion(direccionML.getIdDireccion());
        }

        if (direccionML.getColonia() != null && direccionML.getColonia().getIdColonia() > 0) {
            ColoniaJPA coloniaJPA = new ColoniaJPA();
            coloniaJPA.setIdColonia(direccionML.getColonia().getIdColonia());
            direccionJPA.setColonia(coloniaJPA);
        }
        if (IdUsuario > 0) {
            UsuarioJPA usuarioJPA = new UsuarioJPA();
            usuarioJPA.setIdUsuario(IdUsuario);
            direccionJPA.setUsuario(usuarioJPA);
        }

        return direccionJPA;
    }

}
