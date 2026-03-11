package com.digis01.GGarciaProgramacionNCapasMavenService.DAO.JPA;

import com.digis01.GGarciaProgramacionNCapasMavenService.DAO.IColonia;
import com.digis01.GGarciaProgramacionNCapasMavenService.JPA.Result;
import com.digis01.GGarciaProgramacionNCapasMavenService.JPA.Colonia;
import jakarta.persistence.EntityManager;
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
    public Result GetAllById(int IdMunicipio) {
        Result result = new Result();
        try {
            String jpql = "SELECT c FROM Colonia c WHERE c.municipio.idMunicipio = :idMunicipio ORDER BY c.nombre ASC";
            TypedQuery<Colonia> query = entityManager.createQuery(jpql, Colonia.class);
            query.setParameter("idMunicipio", IdMunicipio);
            List<Colonia> colonias = query.getResultList();
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
            String jpql = "SELECT c FROM Colonia c WHERE c.codigoPostal = :CodigoPostal ORDER BY c.nombre ASC";
            TypedQuery<Colonia> query = entityManager.createQuery(jpql, Colonia.class);
            query.setParameter("CodigoPostal", CodigoPostal);
            List<Colonia> colonias = query.getResultList();
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
