package com.digis01.GGarciaProgramacionNCapasMavenService.DAO;

import com.digis01.GGarciaProgramacionNCapasMavenService.Entity.Result;
import com.digis01.GGarciaProgramacionNCapasMavenService.Entity.Usuario;

import java.util.List;

public interface IUsuario {

    Result GetAll();

    Result GetAllById(int IdUsuario);

    Result DeleteDireccionUsuariobyId(int IdUsuario);

    Result AddUsuarioDireccion(Usuario usuario);

    Result UsuarioDireccionBusqueda(Usuario usuario);

    Result ModifyUsuario(Usuario usuario);

    Result CambiarEstatus(int IdUsuario, int Estatus);

    Result AddAll(List<Usuario> usuarios);
}
