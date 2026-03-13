package com.digis01.GGarciaProgramacionNCapasMavenService.DAO.JPA;

import com.digis01.GGarciaProgramacionNCapasMavenService.DAO.IEstado;
import com.digis01.GGarciaProgramacionNCapasMavenService.Entity.Estado;
import com.digis01.GGarciaProgramacionNCapasMavenService.Entity.Result;
import jakarta.persistence.EntityManager;
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
            String jpql = "SELECT e FROM Estado e WHERE e.pais.idPais = :idPais ORDER BY e.nombre ASC";
            TypedQuery<Estado> query = entityManager.createQuery(jpql, Estado.class);
            query.setParameter("idPais", idPais);
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
