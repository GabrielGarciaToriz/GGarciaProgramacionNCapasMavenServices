package com.digis01.GGarciaProgramacionNCapasMavenService.JPA;

import jakarta.persistence.*;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private int idUsuario;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellidopaterno")
    private String apellidoPaterno;
    @Column(name = "apellidomaterno")
    private String apellidoMaterno;
    @Column(name = "celular")
    private String celular;
    @Column(name = "curp")
    private String curp;
    @Column(name = "username")
    private String userName;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "sexo")
    private String sexo;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "fechanacimiento")
    private Date fechaNacimiento;
    @Column(name = "estatus")
    private int estatus;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idrol")
    public Rol rol;
    @OneToMany(mappedBy = "usuario", orphanRemoval = true, cascade = CascadeType.ALL)
    public List<Direccion> direcciones;

}
