package com.digis01.GGarciaProgramacionNCapasMavenService.Repository;

import com.digis01.GGarciaProgramacionNCapasMavenService.Entity.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PaisRepository extends JpaRepository<Pais, Integer> {
    List<Pais> findAllByOrderByNombreAsc();
}