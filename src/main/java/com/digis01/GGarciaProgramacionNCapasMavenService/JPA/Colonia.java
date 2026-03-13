package com.digis01.GGarciaProgramacionNCapasMavenService.JPA;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Colonia")
@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Entidad que representa una colonia o asentamiento")
public class Colonia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcolonia")
    @Schema(description = "Identificador único autogenerado de la colonia", example = "105", accessMode = Schema.AccessMode.READ_ONLY)
    private int idColonia;

    @Column(name = "nombre")
    @Schema(description = "Nombre de la colonia", example = "Roma Norte")
    private String nombre;

    @Column(name = "codigopostal")
    @Schema(description = "Código Postal asignado a la colonia", example = "06700")
    private String codigoPostal;

    @ManyToOne
    @JoinColumn(name = "idmunicipio")
    @Schema(description = "Municipio al que pertenece la colonia")
    public Municipio municipio;
}
