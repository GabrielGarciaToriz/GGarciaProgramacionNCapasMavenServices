package com.digis01.GGarciaProgramacionNCapasMaven.DAO.JPA;

import com.digis01.GGarciaProgramacionNCapasMaven.DAO.IEstado;
import com.digis01.GGarciaProgramacionNCapasMaven.JPA.Estado;
import com.digis01.GGarciaProgramacionNCapasMaven.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("EstadoDAOJPA")
public class EstadoDAOJPAImplementation implements IEstado {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result GetAllById(int idPais) {
        Result result = new Result();
        try {
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("paisestadobyidsp", Estado.class);
            query.registerStoredProcedureParameter(1, void.class, ParameterMode.REF_CURSOR);
            query.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);
            query.setParameter(2, idPais);
            query.execute();
            List<Estado> estados = query.getResultList();
            result.objects = new ArrayList<>(estados);
            result.correct = true;
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;

        }
        return result;
    }

}
