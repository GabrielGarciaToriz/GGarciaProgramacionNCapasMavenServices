package com.digis01.GGarciaProgramacionNCapasMavenService.Repository;

import com.digis01.GGarciaProgramacionNCapasMavenService.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Query("""
           SELECT DISTINCT u FROM Usuario u
           LEFT JOIN FETCH u.rol
           LEFT JOIN FETCH u.direcciones d
           LEFT JOIN FETCH d.colonia c
           LEFT JOIN FETCH c.municipio m
           LEFT JOIN FETCH m.estado e
           LEFT JOIN FETCH e.pais
           ORDER BY u.apellidoPaterno ASC, u.nombre ASC
           """)
    List<Usuario> findAllWithDetails();

    @Query("""
           SELECT u FROM Usuario u
           LEFT JOIN FETCH u.rol
           LEFT JOIN FETCH u.direcciones d
           LEFT JOIN FETCH d.colonia c
           LEFT JOIN FETCH c.municipio m
           LEFT JOIN FETCH m.estado e
           LEFT JOIN FETCH e.pais
           WHERE u.idUsuario = :idUsuario
           """)
    Usuario findByIdWithDetails(@Param("idUsuario") int idUsuario);

    @Query("""
           SELECT DISTINCT u FROM Usuario u
           LEFT JOIN FETCH u.rol
           LEFT JOIN FETCH u.direcciones d
           LEFT JOIN FETCH d.colonia c
           LEFT JOIN FETCH c.municipio m
           LEFT JOIN FETCH m.estado e
           LEFT JOIN FETCH e.pais
           WHERE (:nombre = '' OR LOWER(u.nombre) LIKE LOWER(CONCAT('%', :nombre ,'%')))
           AND (:apellidoPaterno = '' OR LOWER(u.apellidoPaterno) LIKE LOWER(CONCAT('%', :apellidoPaterno, '%')))
           AND (:apellidoMaterno = '' OR LOWER(u.apellidoMaterno) LIKE LOWER(CONCAT('%', :apellidoMaterno, '%')))
           AND (:idRol = 0 OR u.rol.idRol = :idRol)
           ORDER BY u.apellidoPaterno ASC, u.nombre ASC
           """)
    List<Usuario> buscarUsuariosFiltro(@Param("nombre") String nombre, 
                                       @Param("apellidoPaterno") String apellidoPaterno, 
                                       @Param("apellidoMaterno") String apellidoMaterno, 
                                       @Param("idRol") int idRol);

    @Modifying
    @Query("UPDATE Usuario u SET u.estatus = :estatus WHERE u.idUsuario = :idUsuario")
    int cambiarEstatus(@Param("idUsuario") int idUsuario, @Param("estatus") int estatus);
}