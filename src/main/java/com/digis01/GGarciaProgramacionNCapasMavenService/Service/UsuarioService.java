package com.digis01.GGarciaProgramacionNCapasMavenService.Service;

import com.digis01.GGarciaProgramacionNCapasMavenService.DTO.Result;
import com.digis01.GGarciaProgramacionNCapasMavenService.Entity.Usuario;
import com.digis01.GGarciaProgramacionNCapasMavenService.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Result getAll() {
        return envolverResultadoLista(() -> usuarioRepository.findAllWithDetails());
    }

    public Result getAllById(int idUsuario) {
        Result result = new Result();
        try {
            Usuario usuario = usuarioRepository.findByIdWithDetails(idUsuario);
            result.object = usuario;
            result.correct = true;
        } catch (Exception e) {
            manejarExcepcion(result, e);
        }
        return result;
    }

    public Result usuarioDireccionBusqueda(Usuario u) {
        String nombre = u.getNombre() != null ? u.getNombre().trim() : "";
        String apPaterno = u.getApellidoPaterno() != null ? u.getApellidoPaterno().trim() : "";
        String apMaterno = u.getApellidoMaterno() != null ? u.getApellidoMaterno().trim() : "";
        int idRol = u.getRol() != null ? u.getRol().getIdRol() : 0;

        return envolverResultadoLista(() -> usuarioRepository.buscarUsuariosFiltro(nombre, apPaterno, apMaterno, idRol));
    }

    @Transactional
    public Result addOrModifyUsuario(Usuario usuario) {
        Result result = new Result();
        try {
            if (usuario.getDirecciones() != null) {
                usuario.getDirecciones().forEach(dir -> dir.setUsuario(usuario));
            }
            usuarioRepository.save(usuario);
            result.correct = true;
        } catch (Exception e) {
            manejarExcepcion(result, e);
        }
        return result;
    }

    @Transactional
    public Result deleteDireccionUsuariobyId(int idUsuario) {
        Result result = new Result();
        try {
            // El CascadeType.ALL en tu entidad elimina las direcciones automáticamente
            usuarioRepository.deleteById(idUsuario);
            result.correct = true;
        } catch (Exception e) {
            manejarExcepcion(result, e);
        }
        return result;
    }

    @Transactional
    public Result cambiarEstatus(int idUsuario, int estatus) {
        Result result = new Result();
        try {
            int filasAfectadas = usuarioRepository.cambiarEstatus(idUsuario, estatus);
            result.correct = filasAfectadas > 0;
            if (!result.correct) {
                result.errorMessage = "No se encontró el usuario a modificar";
            }
        } catch (Exception e) {
            manejarExcepcion(result, e);
        }
        return result;
    }

    private Result envolverResultadoLista(java.util.function.Supplier<List<Usuario>> consulta) {
        Result result = new Result();
        try {
            result.objects = new ArrayList<>(consulta.get());
            result.correct = true;
        } catch (Exception e) {
            manejarExcepcion(result, e);
        }
        return result;
    }

    private void manejarExcepcion(Result result, Exception e) {
        result.correct = false;
        result.errorMessage = e.getLocalizedMessage();
        result.ex = e;
    }
}
