package com.digis01.GGarciaProgramacionNCapasMavenService.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "USUARIODETALLEVIEW")
@Immutable
@Getter
@Setter
@NoArgsConstructor
public class UsuarioVista {
    @Id
    @Column(name = "idusuario")
    private int idUsaurio;
    @Column(name = "nombreusuario")
    private String nombreUsaurio;
    @Column(name = "apellidopaterno")
    private String apellidoPaterno;
    @Column(name = "apellidomaterno")
    private String apellidoMaterno;
    @Column(name = "fechanacimiento")
    private Date fechaNacimiento;
    @Column(name = "celular")
    private String celular;
    @Column(name = "curp")
    private String curp;
    @Column(name = "usuario")
    private String usuario;
    @Column(name = "correo")
    private String correo;
    @Column(name = "contraseña")
    private String password;
    @Column(name = "sexo")
    private String sexo;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "esattus")
    private int estatus;
    @Column(name = "idrol")
    private int idRol;
    @Column(name = "rolasignado")
    private String rolAsignado;
    @Column(name = "iddireccion")
    private int idDireccion;
    @Column(name = "calle")
    private String calle;
    @Column(name = "numeroexterior")
    private String numeroExterior;
    @Column(name = "numerointerior")
    private String numeroInterior;
    @Column(name = "idcolonia")
    private int idColonia;
    @Column(name = "colonia")
    private String colonia;
    @Column(name = "cp")
    private String cp;
    @Column(name = "idmunicipio")
    private int idMunicipio;
    @Column(name = "municipio")
    private String municipio;
    @Column(name = "idestado")
    private int idEstado;
    @Column(name = "estado")
    private String estado;
    @Column(name = "idpais")
    private int idPais;
    @Column(name = "pais")
    private String Pais;
}
