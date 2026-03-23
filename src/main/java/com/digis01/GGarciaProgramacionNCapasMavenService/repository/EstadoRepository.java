package com.digis01.GGarciaProgramacionNCapasMavenService.repository;

import com.digis01.GGarciaProgramacionNCapasMavenService.entity.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {
    List<Estado> findByPaisIdPaisOrderByNombreAsc(int idPais);
}