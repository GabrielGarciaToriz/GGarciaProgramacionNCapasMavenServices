package com.digis01.GGarciaProgramacionNCapasMaven.JPA;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "Direccion")
@Getter
@Setter
@NoArgsConstructor
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddireccion")
    private int idDireccion;
    @Column(name = "calle")
    private String calle;
    @Column(name = "numeroexterior")
    private String numeroExterior;
    @Column(name = "numerointerior")
    private String numeroInterior;
    @ManyToOne
    @JoinColumn(name = "idcolonia")
    public Colonia colonia;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idusuario")
    @JsonIgnore
    public Usuario usuario;
}
