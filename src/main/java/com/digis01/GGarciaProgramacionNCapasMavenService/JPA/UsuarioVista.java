package com.digis01.GGarciaProgramacionNCapasMavenService.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "usuariodetalleview")
@Immutable
@Getter
@Setter
@NoArgsConstructor
public class UsuarioVista {

    @Id
    @Column(name = "idusuario")
    private Integer idUsaurio;
    @Column(name = "nombreusuario")
    private String nombre;
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
    @Column(name = "estatus")
    private Integer estatus;
    @Lob
    @Column(name = "imagen")
    private String imagen;
    @Column(name = "idrol")
    private Integer idRol;
    @Column(name = "rolasignado")
    private String rolAsignado;
    @Column(name = "iddireccion")
    private Integer idDireccion;
    @Column(name = "calle")
    private String calle;
    @Column(name = "numeroexterior")
    private String numeroExterior;
    @Column(name = "numerointerior")
    private String numeroInterior;
    @Column(name = "idcolonia")
    private Integer idColonia;
    @Column(name = "colonia")
    private String colonia;
    @Column(name = "cp")
    private String cp;
    @Column(name = "idmunicipio")
    private Integer idMunicipio;
    @Column(name = "municipio")
    private String municipio;
    @Column(name = "idestado")
    private Integer idEstado;
    @Column(name = "estado")
    private String estado;
    @Column(name = "idpais")
    private Integer idPais;
    @Column(name = "pais")
    private String Pais;
}
