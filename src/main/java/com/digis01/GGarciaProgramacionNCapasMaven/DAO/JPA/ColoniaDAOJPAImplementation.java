package com.digis01.GGarciaProgramacionNCapasMaven.DAO.JPA;

import com.digis01.GGarciaProgramacionNCapasMaven.DAO.IColonia;
import com.digis01.GGarciaProgramacionNCapasMaven.ML.Result;
import com.digis01.GGarciaProgramacionNCapasMaven.JPA.ColoniaJPA;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("ColoniaDAOJPA")
public class ColoniaDAOJPAImplementation implements IColonia {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result GetAll(int IdMunicipio) {
        Result result = new Result();
        try {
            String jpql = "SELECT c FROM ColoniaJPA c WHERE c.municipio.idmunicipio = :idMunicipio";
            TypedQuery<ColoniaJPA> query = entityManager.createQuery(jpql, ColoniaJPA.class);
            query.setParameter("idMunicipio", IdMunicipio);
            List<ColoniaJPA> colonias = query.getResultList();
            result.objects = new ArrayList<>(colonias);
            result.correct = true;
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }

    @Override
    public Result GetByCodigoPostal(String CodigoPostal) {
        Result result = new Result();
        try {
            String jpql = "Select c FROM ColoniaJPA c WHERE c.codigoPostal = :CodigoPostal";
            TypedQuery<ColoniaJPA> query = entityManager.createQuery(jpql, ColoniaJPA.class);
            query.setParameter("CodigoPostal", CodigoPostal);
            List<ColoniaJPA> colonias = query.getResultList();
            result.objects = new ArrayList<>(colonias);
            result.correct = true;
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }
}
