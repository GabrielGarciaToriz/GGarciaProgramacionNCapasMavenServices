package com.digis01.GGarciaProgramacionNCapasMaven.DAO.JPA;

import com.digis01.GGarciaProgramacionNCapasMaven.DAO.IRol;
import com.digis01.GGarciaProgramacionNCapasMaven.JPA.Rol;
import com.digis01.GGarciaProgramacionNCapasMaven.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;

@Repository("RolDAOJPA")
public class RolDAOJPAImplementation implements IRol {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result GetAll() {
        Result result = new Result();
        try {
            TypedQuery<Rol> query = entityManager.createQuery("SELECT r from RolJPA r", Rol.class);
            result.objects = new ArrayList<>(query.getResultList());
            result.correct = true;
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }
}
