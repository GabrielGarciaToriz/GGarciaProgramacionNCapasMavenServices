package com.digis01.GGarciaProgramacionNCapasMavenService.DAO.JPA;

import com.digis01.GGarciaProgramacionNCapasMavenService.DAO.IMunicipio;
import com.digis01.GGarciaProgramacionNCapasMavenService.JPA.Result;
import com.digis01.GGarciaProgramacionNCapasMavenService.JPA.Municipio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("MunicipioDAOJPA")
public class MunicipioDAOJPAImplementation implements IMunicipio {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result GetAllById(int IdEstado) {
        Result result = new Result();
        try {
            String jpql = "SELECT m FROM Municipio m WHERE m.estado.idEstado = :idEstado ORDER BY m.nombre ASC";
            TypedQuery<Municipio> query = entityManager.createQuery(jpql, Municipio.class);
            query.setParameter("idEstado", IdEstado);
            List<Municipio> municipios = query.getResultList();
            result.objects = new ArrayList<>(municipios);
            result.correct = true;
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;

    }

}
