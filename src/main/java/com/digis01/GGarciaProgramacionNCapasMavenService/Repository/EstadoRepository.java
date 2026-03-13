package com.digis01.GGarciaProgramacionNCapasMavenService.Repository;

import com.digis01.GGarciaProgramacionNCapasMavenService.Entity.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {
    List<Estado> findByPaisIdPaisOrderByNombreAsc(int idPais);
}