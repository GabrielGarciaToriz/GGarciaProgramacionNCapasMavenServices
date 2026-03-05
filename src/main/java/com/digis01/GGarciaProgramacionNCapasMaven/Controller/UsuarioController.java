package com.digis01.GGarciaProgramacionNCapasMaven.Controller;

import com.digis01.GGarciaProgramacionNCapasMaven.DAO.JDBC.ColoniaDAOImplmentation;
import com.digis01.GGarciaProgramacionNCapasMaven.DAO.JDBC.EstadoDAOImplementation;
import com.digis01.GGarciaProgramacionNCapasMaven.DAO.JDBC.MunicipioDAOImplementation;
import com.digis01.GGarciaProgramacionNCapasMaven.DAO.JDBC.PaisDAOImplementation;
import com.digis01.GGarciaProgramacionNCapasMaven.DAO.JDBC.RolDAOImplementation;
import com.digis01.GGarciaProgramacionNCapasMaven.DAO.JDBC.UsuarioDAOImplementation;
import com.digis01.GGarciaProgramacionNCapasMaven.DAO.JDBC.DireccionDAOImplementation;
import com.digis01.GGarciaProgramacionNCapasMaven.DAO.JPA.ColoniaDAOJPAImplementation;
import com.digis01.GGarciaProgramacionNCapasMaven.DAO.JPA.DireccionDAOJPAImplementation;
import com.digis01.GGarciaProgramacionNCapasMaven.DAO.JPA.EstadoDAOJPAImplementation;
import com.digis01.GGarciaProgramacionNCapasMaven.DAO.JPA.MunicipioDAOJPAImplementation;
import com.digis01.GGarciaProgramacionNCapasMaven.DAO.JPA.PaisDAOJPAImplementation;
import com.digis01.GGarciaProgramacionNCapasMaven.DAO.JPA.RolDAOJPAImplementation;
import com.digis01.GGarciaProgramacionNCapasMaven.DAO.JPA.UsuarioDAOJPAImplementation;
import com.digis01.GGarciaProgramacionNCapasMaven.ML.Direccion;
import com.digis01.GGarciaProgramacionNCapasMaven.ML.Colonia;
import com.digis01.GGarciaProgramacionNCapasMaven.ML.ErroresArchivo;
import com.digis01.GGarciaProgramacionNCapasMaven.ML.Estado;
import com.digis01.GGarciaProgramacionNCapasMaven.ML.Municipio;
import com.digis01.GGarciaProgramacionNCapasMaven.ML.Pais;
import com.digis01.GGarciaProgramacionNCapasMaven.ML.Result;
import com.digis01.GGarciaProgramacionNCapasMaven.ML.Rol;
import com.digis01.GGarciaProgramacionNCapasMaven.ML.Usuario;
import com.digis01.GGarciaProgramacionNCapasMaven.Service.ValidiationService;
import jakarta.validation.Valid;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import java.text.SimpleDateFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

@Controller
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    private ValidiationService validationService;
    /*JDBC*/
    @Autowired
    private UsuarioDAOImplementation usuarioDAOImplementation;
    @Autowired
    private PaisDAOImplementation paisDAOImplementation;
    @Autowired
    private RolDAOImplementation rolDAOImplementation;
    @Autowired
    private MunicipioDAOImplementation municipioDAOImplementation;
    @Autowired
    private ColoniaDAOImplmentation coloniaDAOImplmentation;
    @Autowired
    private DireccionDAOImplementation direccionDAOImplementation;
    @Autowired
    private EstadoDAOImplementation estadoDAOImplementation;
    /*JPA*/
    @Autowired
    private UsuarioDAOJPAImplementation UsuarioDAOJPAImplementation;
    @Autowired
    private RolDAOJPAImplementation RolDAOJPAImplementation;
    @Autowired
    private PaisDAOJPAImplementation PaisDAOJPAImplementation;
    @Autowired
    private EstadoDAOJPAImplementation EstadoDAOJPAImplementation;
    @Autowired
    private MunicipioDAOJPAImplementation MunicipioDAOJPAImplementation;
    @Autowired
    private ColoniaDAOJPAImplementation ColoniaDAOJPAImplementation;
    @Autowired
    private DireccionDAOJPAImplementation DireccionDAOJPAImplementation;
    /*
        Carga los datos de todos los usuarios en una vsita para seleccionar si se deben de editar o eliminar
        - Falta 
     */
    @GetMapping("")
    public String Usuario(Model model) {
        Usuario usuarioBusqueda = new Usuario();
        usuarioBusqueda.setRol(new Rol());
        model.addAttribute("usuarioBusqueda", usuarioBusqueda);
        model.addAttribute("roles", RolDAOJPAImplementation.GetAll().objects);
        model.addAttribute("usuarios", UsuarioDAOJPAImplementation.GetAll().objects);
        return "Usuario";
    }

    /*Carga en la vista los datos de los roles,paises y el modelo de usuario*/
    @GetMapping("form")
    public String FormularioUsuario(Model model) {
        Usuario usuario = new Usuario();
        usuario.setRol(new Rol());
        Direccion direccion = new Direccion();
        Colonia colonia = new Colonia();
        Municipio municipio = new Municipio();
        Estado estado = new Estado();
        Pais pais = new Pais();

        estado.setPais(pais);
        municipio.setEstado(estado);
        colonia.setMunicipio(municipio);
        direccion.setColonia(colonia);

        usuario.setDirecciones(new ArrayList<>());
        usuario.getDirecciones().add(direccion);

        LocalDate fechaMax = LocalDate.now().minusYears(-18);
        model.addAttribute("fechaMaxima", fechaMax.toString());
        model.addAttribute("usuario", usuario);
        model.addAttribute("paises", PaisDAOJPAImplementation.GetAll().objects);
        model.addAttribute("roles", RolDAOJPAImplementation.GetAll().objects);
        return "UsuarioForm";
    }

    /*Envia los datos del usuario a la vista detalle para su edicion o eliminacion*/
    @GetMapping("detail/{IdUsuario}")
    public String DetalleUsuario(@PathVariable("IdUsuario") int IdUsuario, Model model) {
        Result result = UsuarioDAOJPAImplementation.GetAllById(IdUsuario);
        model.addAttribute("usuario", result.objects.get(0));
        model.addAttribute("roles", RolDAOJPAImplementation.GetAll().objects);
        model.addAttribute("paises", PaisDAOJPAImplementation.GetAll().objects);

        Usuario usuario = new Usuario();
        usuario.setRol(new Rol());
        Direccion nuevaDireccion = new Direccion();
        Colonia colonia = new Colonia();
        Municipio municipio = new Municipio();
        Estado estado = new Estado();
        Pais pais = new Pais();

        estado.setPais(pais);
        municipio.setEstado(estado);
        colonia.setMunicipio(municipio);
        nuevaDireccion.setColonia(colonia);
        model.addAttribute("nuevaDireccion", nuevaDireccion);

        return "UsuarioDetail";
    }

    @GetMapping("/direccion/editar/{IdDireccion}")
    public String EdicionDireccion(@PathVariable("IdDireccion") int IdDireccion, Model model) {
        Result result = direccionDAOImplementation.DireccionGetAllById(IdDireccion);
        Direccion direccionEditar = (Direccion) result.objects.get(0);
        int idPais = direccionEditar.getColonia().getMunicipio().getEstado().getPais().getIdPais();
        int idEstado = direccionEditar.getColonia().getMunicipio().getEstado().getIdEstado();
        int idMunicipio = direccionEditar.getColonia().getMunicipio().getIdMunicipio();
        model.addAttribute("direccioneditar", direccionEditar);
        model.addAttribute("paises", PaisDAOJPAImplementation.GetAll().objects);
        model.addAttribute("estados", EstadoDAOJPAImplementation.GetAll(idPais).objects);
        model.addAttribute("municipios", MunicipioDAOJPAImplementation.GetAll(idEstado).objects);
        model.addAttribute("colonias", ColoniaDAOJPAImplementation.GetAll(idMunicipio).objects);

        return "fragments/ModalEditarDireccion :: contenidoModla";
    }

    @GetMapping("/cargar")
    public String CargaMasiva() {
        return "UsuarioCargaMasiva";
    }

    @PostMapping("/buscar")
    public String BuscarUsuario(@ModelAttribute("usuarioBusqueda") Usuario usuarioBusqueda, Model model) {
        Result result = usuarioDAOImplementation.UsuarioDireccionBusqueda(usuarioBusqueda);
        model.addAttribute("usuarioBusqueda", usuarioBusqueda);
        model.addAttribute("roles", rolDAOImplementation.GetAll().objects);
        model.addAttribute("usuarios", result.objects);
        return "Usuario";

    }

    /*
        Envia los datos del usuario y su direccion a la base de datos
        - Falta checar a detalle las validaciones del lado del servidor
        - Cargar los datos nuevamente en caso de que el cliente tenga algun fallo
        - Mostrar si el formulario esta llenado correcta o incorrectamente del lado del cliente
     */
    @PostMapping("form")
    public String FormularioUsuario(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        LocalDate fechaMax = LocalDate.now().minusYears(-18);
        model.addAttribute("fechaMaxima", fechaMax.toString());
        if (usuario.getFechaNacimiento() != null) {
            Calendar fechaMayorEdad = Calendar.getInstance();
            fechaMayorEdad.add(Calendar.YEAR, -18);
            if (usuario.getFechaNacimiento().after(fechaMayorEdad.getTime())) {
                bindingResult.rejectValue("FechaNacimiento", "error.usuario", "Debes der mayor de edad para registrarte");
            }
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("paises", paisDAOImplementation.GetAll().objects);
            model.addAttribute("roles", rolDAOImplementation.GetAll().objects);
            try {
                int idPais = usuario.getDirecciones().get(0).getColonia().getMunicipio().getEstado().getPais().getIdPais();
                if (idPais > 0) {
                    model.addAttribute("estados", estadoDAOImplementation.GetAll(idPais).objects);
                }
                int idEstado = usuario.getDirecciones().get(0).getColonia().getMunicipio().getEstado().getIdEstado();
                if (idEstado > 0) {
                    model.addAttribute("municipios", municipioDAOImplementation.GetAll(idEstado));
                }
                int idMunicipio = usuario.getDirecciones().get(0).getColonia().getMunicipio().getIdMunicipio();
                if (idMunicipio > 0) {
                    model.addAttribute("colonias", coloniaDAOImplmentation.GetAll(idMunicipio));
                }
            } catch (Exception e) {
            }
            return "UsuarioForm";
        }
        Result result = usuarioDAOImplementation.Add(usuario);
        if (result.correct) {
            redirectAttributes.addFlashAttribute("mensajeExito", "Usuario registrado con exito");
            return "redirect:/usuario";
        } else {
            model.addAttribute("mensajeError", "Error en la base de datos: " + result.errorMessage);
            model.addAttribute("paises", paisDAOImplementation.GetAll().objects);
            model.addAttribute("roles", rolDAOImplementation.GetAll().objects);
            return "UsuarioForm";
        }

    }

    /*Elimina al usuario y sus direccion */
    @PostMapping("detail/delete/{IdUsuario}")
    public String EliminarDireccionUsuario(@PathVariable("IdUsuario") int IdUsaurio, RedirectAttributes redirectAttributes) {
        Result result = usuarioDAOImplementation.DeleteDireccionUsuariobyId(IdUsaurio);
        if (result.correct) {
            redirectAttributes.addFlashAttribute("mensajeExito", "El registro se ha eliminado ");
        } else {
            redirectAttributes.addFlashAttribute("mensajeError", "Hubo un problema al eliminar: " + result.errorMessage);
        }
        return "redirect:/usuario";
    }

    @PostMapping("detail/delete/direccion/{IdDireccion}")
    public String EliminarDireccion(@PathVariable("IdDireccion") int IdDireccion, RedirectAttributes redirectAttributes) {
        Result result = usuarioDAOImplementation.DeleteDireccionById(IdDireccion);
        if (result.correct) {
            redirectAttributes.addFlashAttribute("mensajeExito", "La dieccion se ha eliminado ");
        } else {
            redirectAttributes.addFlashAttribute("mensajeError", "Huno un problema al eliminar la direccion: " + result.errorMessage);
        }
        return "redirect:/usuario";
    }

    @PostMapping("/editarUsuario")
    public String EditarUsuario(@ModelAttribute("usuario") Usuario usuario, RedirectAttributes redirectAttributes, Model model) {
        if (usuario.getRol() == null || usuario.getRol().getIdRol() == 0) {
            redirectAttributes.addFlashAttribute("mensajeError", "Por favor selecciona un rol valido");
            return "redirect:/usuario";
        }
        Result result = usuarioDAOImplementation.ModifyUsuario(usuario);
        model.addAttribute("roles", rolDAOImplementation.GetAll().objects);
        if (result.correct) {
            redirectAttributes.addFlashAttribute("mensajeExito", "Los datos se han actualizado con exito");
        } else {
            redirectAttributes.addFlashAttribute("mensajeError", "Los datos no se han podido actualizar" + result.errorMessage);
        }
        return "redirect:/usuario";
    }

    /*AGREGAMOS UNA DIRECCION DESPECTO AL ID DEL USUARIO*/
    @PostMapping("/agregarDireccion")
    public String AgregarDireccionUsuario(@ModelAttribute("nuevaDireccion") Direccion nuevaDireccion, @RequestParam("IdUsuario") int IdUsuario, RedirectAttributes redirectAttributes) {
        Result result = direccionDAOImplementation.DireccionAdd(nuevaDireccion, IdUsuario);
        if (result.correct) {
            redirectAttributes.addFlashAttribute("mensajeExito", "Direccion agregada con exito");
        } else {
            redirectAttributes.addFlashAttribute("mensajeError", "Direccion no agregada " + result.errorMessage);
        }
        return "redirect:/usuario/detail/" + IdUsuario;
    }

    /*MODIFICAMOS UNA DIRECCION RESPECTO AL ID DEL USUARIO*/
    @PostMapping("/modificarDireccion")
    public String ModificarDireccionUsuario() {

        return "";
    }

    @PostMapping("/procesarCargaMasiva")
    public String ProcesarCargaMasiva(@RequestParam("archivo") MultipartFile archivo, Model model, RedirectAttributes redirectAttributes) {
        try {
            if (archivo != null && !archivo.isEmpty()) {
                String rutaBae = System.getProperty("user.dir");
                String rutaCarpeta = "src/main/resources/archivosCM";
                String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSS"));
                String nombreArchivo = fecha + "_" + archivo.getOriginalFilename();
                String rutaArchivo = rutaBae + "/" + rutaCarpeta + "/" + nombreArchivo;
                String extension = archivo.getOriginalFilename().substring(archivo.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();

                List<Usuario> usuarios = new ArrayList<>(); // Inicializamos la lista
                File archivoFisico = new File(rutaArchivo);
                archivoFisico.getParentFile().mkdirs();

                if (extension.equals("txt")) {
                    archivo.transferTo(archivoFisico);
                    usuarios = LecturaArchivoTXT(archivoFisico);
                } else if (extension.equals("xlsx")) {
                    archivo.transferTo(archivoFisico);
                    usuarios = LecturaArchivoExcel(archivoFisico);
                } else {
                    model.addAttribute("mensajeError", "Extensión errónea, manda archivos en formato .txt o .xlsx");
                    return "UsuarioCargaMasiva";
                }

                if (usuarios == null) {
                    model.addAttribute("mensajeError", "Hubo un error crítico al leer el archivo. Verifica que el formato de las celdas sea correcto (ej. IDs numéricos).");
                    return "UsuarioCargaMasiva"; // Retornamos a la vista
                }

                List<ErroresArchivo> errores = ValidarDatos(usuarios);

                if (!errores.isEmpty()) {
                    model.addAttribute("errores", errores);
                    return "UsuarioCargaMasiva";
                } else {
                    //AQUI ES DONDE A EL BATCH
                    redirectAttributes.addFlashAttribute("mensajeExito", "Archivo cargado correctamente");
                    return "redirect:/usuario";
                }
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getLocalizedMessage());
        }
        return "redirect:/usuario";
    }

    public List<Usuario> LecturaArchivoTXT(File archivo) {
        List<Usuario> usuarios = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy");
        try (InputStream inputStream = new FileInputStream(archivo); BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            usuarios = new ArrayList<>();
            String cadena = "";
            while ((cadena = bufferedReader.readLine()) != null) {
                String[] datoUsuario = cadena.split("\\|");
                if (datoUsuario.length < 2) {
                    continue;
                }
                Usuario usuario = new Usuario();
                usuario.setNombre(datoUsuario[0]);
                usuario.setApellidoPaterno(datoUsuario[1]);
                usuario.setApellidoMaterno(datoUsuario[2]);
                usuario.setCelular(datoUsuario[3]);
                usuario.setCurp(datoUsuario[4]);
                usuario.setUserName(datoUsuario[5]);
                usuario.setEmail(datoUsuario[6]);
                usuario.setPassword(datoUsuario[7]);
                usuario.setSexo(datoUsuario[8]);
                usuario.setTelefono(datoUsuario[9]);
                String FechaNacimiento = datoUsuario[10];
                if (FechaNacimiento != null && !FechaNacimiento.trim().isEmpty()) {
                    try {
                        Date fechaNacimiento = simpleDateFormat.parse(FechaNacimiento);
                        usuario.setFechaNacimiento(fechaNacimiento);

                    } catch (Exception e) {
                        System.out.println("Formato no valido para fecha txt");
                    }
                }
                usuario.setRol(new Rol());
                usuario.getRol().setIdRol(Integer.parseInt(datoUsuario[11]));
                Direccion direccion = new Direccion();
                direccion.setCalle(datoUsuario[12]);
                direccion.setNumeroExterior(datoUsuario[13]);
                direccion.setNumeroInterior(datoUsuario[14]);
                direccion.setColonia(new Colonia());
                direccion.Colonia.setIdColonia(Integer.parseInt(datoUsuario[15]));

                usuario.Direcciones = new ArrayList<>();
                usuario.Direcciones.add(direccion);
                usuarios.add(usuario);
            }

        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return usuarios;

    }

    public List<Usuario> LecturaArchivoExcel(File archivo) {
        List<Usuario> usuarios = new ArrayList<>();
        SimpleDateFormat FechaNacimineto = new SimpleDateFormat("dd/MM/yy");
        try (InputStream inputStream = new FileInputStream(archivo); XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            XSSFSheet hoja = workbook.getSheetAt(0);
            for (Row row : hoja) {
                if (row.getRowNum() == 0) {
                    continue;
                }
                Usuario usuario = new Usuario();
                if (row.getCell(0) != null) {
                    usuario.setNombre(row.getCell(0).toString());
                }
                if (row.getCell(1) != null) {
                    usuario.setApellidoPaterno(row.getCell(1).toString());
                }
                if (row.getCell(2) != null) {
                    usuario.setApellidoMaterno(row.getCell(2).toString());
                }
                if (row.getCell(3) != null) {
                    usuario.setCelular(row.getCell(3).toString());
                }
                if (row.getCell(4) != null) {
                    usuario.setNombre(row.getCell(4).toString());
                }
                if (row.getCell(5) != null) {
                    usuario.setCurp(row.getCell(5).toString());
                }
                if (row.getCell(6) != null) {
                    usuario.setUserName(row.getCell(6).toString());
                }
                if (row.getCell(7) != null) {
                    usuario.setEmail(row.getCell(7).toString());
                }
                if (row.getCell(8) != null) {
                    usuario.setPassword(row.getCell(8).toString());
                }
                if (row.getCell(9) != null) {
                    usuario.setSexo(row.getCell(9).toString());
                }
                if (row.getCell(10) != null) {
                    usuario.setTelefono(row.getCell(10).toString());
                }
                Cell celdaFecha = row.getCell(11);
                if (celdaFecha != null) {
                    if (celdaFecha.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(celdaFecha)) {
                        usuario.setFechaNacimiento(celdaFecha.getDateCellValue());
                    } else if (celdaFecha.getCellType() == CellType.STRING) {
                        try {
                            Date FormatoFecha = FechaNacimineto.parse(celdaFecha.getStringCellValue());
                            usuario.setFechaNacimiento(FormatoFecha);
                        } catch (Exception e) {
                        }
                    }
                }
                if (row.getCell(12) != null) {
                    usuario.setRol(new Rol());
                    usuario.Rol.setIdRol(Integer.parseInt(row.getCell(12).toString()));
                }

                Direccion direccion = new Direccion();
                direccion.setColonia(new Colonia());
                if (row.getCell(13) != null) {
                    direccion.setCalle(row.getCell(13).toString());
                }
                if (row.getCell(14) != null) {
                    direccion.setNumeroExterior(row.getCell(14).toString());
                }
                if (row.getCell(15) != null) {
                    direccion.setNumeroInterior(row.getCell(15).toString());
                }
                if (row.getCell(16) != null) {
                    direccion.Colonia.setIdColonia(Integer.parseInt(row.getCell(16).toString()));
                }
                usuario.Direcciones = new ArrayList<>();
                usuario.Direcciones.add(direccion);
                usuarios.add(usuario);
            }
        } catch (Exception e) {
            return null;
        }
        return usuarios;
    }

    public List<ErroresArchivo> ValidarDatos(List<Usuario> usuarios) {
        List<ErroresArchivo> errores = new ArrayList<>();

        int numeroFila = 1;
        for (Usuario usuario : usuarios) {
            BindingResult bindingResult = validationService.validateResult(usuario);
            if (bindingResult.hasErrors()) {
                for (ObjectError objectError : bindingResult.getAllErrors()) {
                    if (objectError instanceof FieldError) {
                        ErroresArchivo errorCarga = new ErroresArchivo();
                        FieldError fieldError = (FieldError) objectError;
                        errorCarga.dato = fieldError.getField();
                        errorCarga.descripcion = fieldError.getDefaultMessage();
                        errorCarga.fila = numeroFila;
                        errores.add(errorCarga);
                    }
                }
            }
            numeroFila++;
        }
        return errores;
    }

    /*Cargar los datos del estado */
    @GetMapping("getEstadoByPais/{IdPais}")
    @ResponseBody
    public Result getEstadoByPais(@PathVariable("IdPais") int IdPais) {
        Result result = estadoDAOImplementation.GetAll(IdPais);
        return result;
    }

    /*Cargar los datos del municipio*/
    @GetMapping("getMunicipioByEstado/{IdEstado}")
    @ResponseBody
    public Result getMunicipioByEstado(@PathVariable("IdEstado") int IdEstado) {
        Result result = municipioDAOImplementation.GetAll(IdEstado);
        return result;
    }

    /*Cargar los datos del colonia*/
    @GetMapping("getColoniabyMunicipio/{IdMunicipio}")
    @ResponseBody
    public Result getColoniabyMunicipio(@PathVariable("IdMunicipio") int IdMunicipio) {
        Result result = coloniaDAOImplmentation.GetAll(IdMunicipio);
        return result;
    }

    /*Buscar la colonia usando el codigo postal*/
    @GetMapping("getDireccionByCodigoPostal/{CodigoPostal}")
    @ResponseBody
    public Result getDireccionByCodigoPostal(@PathVariable("CodigoPostal") String CodigoPostal) {
        Result result = coloniaDAOImplmentation.GetByCodigoPostal(CodigoPostal);
        return result;
    }

    @PostMapping("/cambiarEstatus")
    @ResponseBody
    public Result CambiarEstatusUsuario(@RequestParam("IdUsuario") int IdUsuario, @RequestParam("Estatus") int Estatus) {
        Result result = UsuarioDAOJPAImplementation.CambiarEstatus(IdUsuario, Estatus);
        return result;
    }

}
