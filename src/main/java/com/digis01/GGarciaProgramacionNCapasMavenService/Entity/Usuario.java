package com.digis01.GGarciaProgramacionNCapasMavenService.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Entidad que representa a un usuario registrado en el sistema")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    @Schema(description = "Identificador único autogenerado del usuario", example = "10", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer idUsuario;

    @Column(name = "nombre")
    @Schema(description = "Nombre(s) del usuario", example = "Carlos", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @Column(name = "apellidopaterno")
    @Schema(description = "Apellido paterno", example = "García")
    private String apellidoPaterno;

    @Column(name = "apellidomaterno")
    @Schema(description = "Apellido materno", example = "López")
    private String apellidoMaterno;

    @Column(name = "celular")
    @Schema(description = "Número de teléfono celular (10 dígitos)", example = "5512345678")
    private String celular;

    @Column(name = "curp")
    @Schema(description = "Clave Única de Registro de Población (18 caracteres)", example = "GALC901012HDFRXX01")
    private String curp;

    @Column(name = "username")
    @Schema(description = "Nombre de usuario para iniciar sesión", example = "cgarcia")
    private String userName;

    @Column(name = "email")
    @Schema(description = "Correo electrónico de contacto", example = "carlos@example.com")
    private String email;

    @Column(name = "password")
    @Schema(description = "Contraseña encriptada", example = "MiPasswordSeguro123")
    private String password;

    @Column(name = "sexo")
    @Schema(description = "Género del usuario (ej. 'M' para Masculino, 'F' para Femenino)", example = "M")
    private String sexo;

    @Column(name = "telefono")
    @Schema(description = "Número de teléfono fijo", example = "5598765432")
    private String telefono;

    @Column(name = "fechanacimiento")
    @Schema(description = "Fecha de nacimiento del usuario", example = "1990-10-12T00:00:00.000Z")
    private Date fechaNacimiento;

    @Column(name = "estatus")
    @Schema(description = "Estado lógico del usuario (1 = Activo, 0 = Inactivo)", example = "1")
    private int estatus;

    @Lob
    @Column(name = "imagen")
    @Schema(description = "Fotografía de perfil codificada en Base64", example = "iVBORw0KGgoAAAANSUhEUgAAAAE...")
    private String imagen;

    @ManyToOne()
    @JoinColumn(name = "idrol")
    @Schema(description = "Rol asignado al usuario con sus permisos correspondientes")
    public Rol rol;

    @OneToMany(mappedBy = "usuario", orphanRemoval = true, cascade = CascadeType.ALL)
    @Schema(description = "Lista de direcciones registradas por este usuario")
    public List<Direccion> direcciones;
}