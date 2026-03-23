package com.digis01.GGarciaProgramacionNCapasMavenService.repository;

import com.digis01.GGarciaProgramacionNCapasMavenService.entity.Colonia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ColoniaRepository extends JpaRepository<Colonia, Integer> {
    List<Colonia> findByMunicipioIdMunicipioOrderByNombreAsc(int idMunicipio);
    List<Colonia> findByCodigoPostalOrderByNombreAsc(String codigoPostal);
}