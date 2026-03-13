package com.digis01.GGarciaProgramacionNCapasMavenService.Repository;

import com.digis01.GGarciaProgramacionNCapasMavenService.Entity.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Integer> {
    List<Direccion> findByUsuarioIdUsuario(int idUsuario);
}