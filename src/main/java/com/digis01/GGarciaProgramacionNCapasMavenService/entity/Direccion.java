package com.digis01.GGarciaProgramacionNCapasMavenService.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Direccion")
@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Entidad que representa un domicilio asociado a un usuario")
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddireccion")
    @Schema(description = "Identificador único autogenerado de la dirección", example = "5", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer idDireccion;

    @Column(name = "calle")
    @Schema(description = "Nombre de la calle, avenida o vialidad", example = "Av. Paseo de la Reforma")
    private String calle;

    @Column(name = "numeroexterior")
    @Schema(description = "Número exterior del domicilio", example = "222")
    private String numeroExterior;

    @Column(name = "numerointerior")
    @Schema(description = "Número interior, departamento o letra (si aplica)", example = "Depto 4B", nullable = true)
    private String numeroInterior;

    @ManyToOne
    @JoinColumn(name = "idcolonia")
    @Schema(description = "Colonia a la que pertenece esta dirección")
    public Colonia colonia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idusuario")
    @JsonIgnore
    public Usuario usuario;
}