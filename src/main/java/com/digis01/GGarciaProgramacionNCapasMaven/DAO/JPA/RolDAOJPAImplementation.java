package com.digis01.GGarciaProgramacionNCapasMaven.DAO.JPA;

import com.digis01.GGarciaProgramacionNCapasMaven.DAO.IRol;
import com.digis01.GGarciaProgramacionNCapasMaven.ML.Rol;
import com.digis01.GGarciaProgramacionNCapasMaven.JPA.RolJPA;
import com.digis01.GGarciaProgramacionNCapasMaven.ML.Result;
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
            TypedQuery<RolJPA> query = entityManager.createQuery("SELECT r from RolJPA r", RolJPA.class);
            List<RolJPA> resultListJPA = query.getResultList();
            ModelMapper modelMapper = new ModelMapper();
            List<Rol> resultListML = new ArrayList<>();
            for (RolJPA jpaRol : resultListJPA) {
                Rol rolML = modelMapper.map(jpaRol, Rol.class);
                resultListML.add(rolML);
            }

            result.objects = new ArrayList<>(resultListML);
            result.correct = true;
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }
}
