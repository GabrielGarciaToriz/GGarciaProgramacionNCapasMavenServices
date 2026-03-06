package com.digis01.GGarciaProgramacionNCapasMaven.DAO;

import com.digis01.GGarciaProgramacionNCapasMaven.JPA.Result;
import com.digis01.GGarciaProgramacionNCapasMaven.JPA.Usuario;

import java.util.List;

public interface IUsuario {

    Result GetAll();

    Result GetAllById(int IdUsuario);

    Result DeleteDireccionUsuariobyId(int IdUsuario);

    Result DeleteDireccionById(int IdDireccion);

    Result Add(Usuario usuario);

    Result UsuarioDireccionBusqueda(Usuario usuario);

    Result ModifyUsuario(Usuario usuario);

    Result CambiarEstatus(int IdUsuario, int Estatus);

    Result AddAll(List<Usuario> usuarios);
}
