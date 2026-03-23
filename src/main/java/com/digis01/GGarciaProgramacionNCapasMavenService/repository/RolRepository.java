package com.digis01.GGarciaProgramacionNCapasMavenService.repository;

import com.digis01.GGarciaProgramacionNCapasMavenService.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    // Al heredar de JpaRepository ya tenemos el findAll() por defecto
}