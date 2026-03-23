package com.digis01.GGarciaProgramacionNCapasMavenService.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Rol")
@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Entidad que define los niveles de acceso o roles dentro del sistema")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idrol")
    @Schema(description = "Identificador único autogenerado del rol", example = "2", accessMode = Schema.AccessMode.READ_ONLY)
    private int idRol;
    @Column(name = "nombrerol")
    @Schema(description = "Nombre descriptivo del rol", example = "Administrador")
    private String nombre;
}
