package com.digis01.GGarciaProgramacionNCapasMaven.JPA;

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
@Table(name = "Colonia")
@Getter
@Setter
@NoArgsConstructor
public class ColoniaJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcolonia")
    private int idColonia;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "codigopostal")
    private String codigoPostal;
    @ManyToOne
    @JoinColumn(name = "idmunicipio")
    public MunicipioJPA municipio;
}
