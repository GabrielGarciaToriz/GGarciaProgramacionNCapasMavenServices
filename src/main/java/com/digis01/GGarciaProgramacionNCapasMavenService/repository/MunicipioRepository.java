package com.digis01.GGarciaProgramacionNCapasMavenService.repository;

import com.digis01.GGarciaProgramacionNCapasMavenService.entity.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MunicipioRepository extends JpaRepository<Municipio, Integer> {
    List<Municipio> findByEstadoIdEstadoOrderByNombreAsc(int idEstado);
}