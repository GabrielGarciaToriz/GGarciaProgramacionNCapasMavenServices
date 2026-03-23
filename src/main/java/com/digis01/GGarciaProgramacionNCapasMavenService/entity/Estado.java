package com.digis01.GGarciaProgramacionNCapasMavenService.entity;

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
@Table(name = "Estado")
@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Entidad que representa un estado, departamento o provincia")
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idestado")
    @Schema(description = "Identificador único autogenerado del estado", example = "9", accessMode = Schema.AccessMode.READ_ONLY)
    private int idEstado;

    @Column(name = "nombre")
    @Schema(description = "Nombre del estado", example = "Ciudad de México")
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "idpais")
    @Schema(description = "País al que pertenece este estado")
    public Pais pais;
}