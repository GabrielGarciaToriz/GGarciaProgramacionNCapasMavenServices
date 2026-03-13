package com.digis01.GGarciaProgramacionNCapasMavenService.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Pais")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad que representa un país dentro del catálogo geográfico")
public class Pais {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpais")
    @Schema(description = "Identificador único autogenerado del país", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private int idPais;

    @Column(name = "nombre")
    @Schema(description = "Nombre oficial del país", example = "México")
    private String nombre;

}