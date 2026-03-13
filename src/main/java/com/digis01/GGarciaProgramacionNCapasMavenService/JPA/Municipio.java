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
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Municipio")
@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Entidad que representa un municipio o alcaldía")
public class Municipio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idmunicipio")
    @Schema(description = "Identificador único autogenerado del municipio", example = "15", accessMode = Schema.AccessMode.READ_ONLY)
    private int idMunicipio;

    @Column(name = "nombre")
    @Schema(description = "Nombre del municipio o alcaldía", example = "Cuauhtémoc")
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "idestado")
    @Schema(description = "Estado al que pertenece este municipio")
    public Estado estado;
}