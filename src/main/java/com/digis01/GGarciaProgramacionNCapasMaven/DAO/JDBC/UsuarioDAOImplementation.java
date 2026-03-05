package com.digis01.GGarciaProgramacionNCapasMaven.DAO.JDBC;

import com.digis01.GGarciaProgramacionNCapasMaven.DAO.IUsuario;
import com.digis01.GGarciaProgramacionNCapasMaven.ML.Colonia;
import com.digis01.GGarciaProgramacionNCapasMaven.ML.Result;
import com.digis01.GGarciaProgramacionNCapasMaven.ML.Usuario;
import com.digis01.GGarciaProgramacionNCapasMaven.ML.Direccion;
import com.digis01.GGarciaProgramacionNCapasMaven.ML.Estado;
import com.digis01.GGarciaProgramacionNCapasMaven.ML.Municipio;
import com.digis01.GGarciaProgramacionNCapasMaven.ML.Pais;
import com.digis01.GGarciaProgramacionNCapasMaven.ML.Rol;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UsuarioDAOImplementation implements IUsuario {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result GetAll() {
        Result result = new Result();
        result.objects = new ArrayList<>();
        try {
            jdbcTemplate.execute("{CALL UsuarioDireccionGetAllSP(?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                        callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                        callableStatement.execute();
                        ResultSet resultSet = (ResultSet) callableStatement.getObject(1);

                        while (resultSet.next()) {
                            int IdUsuario = resultSet.getInt("IdUsuario");
                            if (!result.objects.isEmpty() && IdUsuario == ((Usuario) (result.objects.get(result.objects.size() - 1))).getIdUsuario()) {
                                Direccion direccion = new Direccion();
                                direccion.setIdColonia(resultSet.getInt("IdColonia"));
                                direccion.setCalle(resultSet.getString("Calle"));
                                direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                                direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                                direccion.Colonia = new Colonia();
                                direccion.Colonia.setNombre(resultSet.getString("Colonia"));
                                direccion.Colonia.setCodigoPostal(resultSet.getString("CP"));
                                direccion.Colonia.Municipio = new Municipio();
                                direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                                direccion.Colonia.Municipio.setNombre(resultSet.getString("Municipio"));
                                direccion.Colonia.Municipio.Estado = new Estado();
                                direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                                direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("Estado"));
                                direccion.Colonia.Municipio.Estado.Pais = new Pais();
                                direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                                direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("Pais"));
                                ((Usuario) (result.objects.get(result.objects.size() - 1))).Direcciones.add(direccion);
                            } else {
                                Usuario usuario = new Usuario();
                                usuario.Rol = new Rol();
                                usuario.Direcciones = new ArrayList<>();
                                usuario.setIdUsuario(IdUsuario);
                                usuario.setNombre(resultSet.getString("NombreUsuario"));
                                usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                                usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                                usuario.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
                                usuario.setCelular(resultSet.getString("Celular"));
                                usuario.setCurp(resultSet.getString("Curp"));
                                usuario.setUserName(resultSet.getString("Usuario"));
                                usuario.setEmail(resultSet.getString("Email"));
                                usuario.setPassword(resultSet.getString("Contraseña"));
                                usuario.setSexo(resultSet.getString("Sexo"));
                                usuario.setTelefono(resultSet.getString("Telefono"));
                                usuario.setEstatus(resultSet.getInt("Estatus"));
                                usuario.Rol.setIdRol(resultSet.getInt("IdRol"));
                                usuario.Rol.setNombre(resultSet.getString("RolAsignado"));
                                Direccion direccion = new Direccion();
                                direccion.setIdColonia(resultSet.getInt("IdColonia"));
                                direccion.setCalle(resultSet.getString("Calle"));
                                direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                                direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                                direccion.Colonia = new Colonia();
                                direccion.Colonia.setNombre(resultSet.getString("Colonia"));
                                direccion.Colonia.setCodigoPostal(resultSet.getString("CP"));
                                direccion.Colonia.Municipio = new Municipio();
                                direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                                direccion.Colonia.Municipio.setNombre(resultSet.getString("Municipio"));
                                direccion.Colonia.Municipio.Estado = new Estado();
                                direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                                direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("Estado"));
                                direccion.Colonia.Municipio.Estado.Pais = new Pais();
                                direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                                direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("Pais"));
                                usuario.Direcciones.add(direccion);
                                result.objects.add(usuario);
                            }
                        }
                        return result.correct;
                    }
            );
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }

    @Override
    public Result GetAllById(int IdUsuario) {
        Result result = new Result();
        result.objects = new ArrayList<>();
        try {
            jdbcTemplate.execute("{CALL UsuarioDireccionGetAllByIdSP(?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                        callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                        callableStatement.setInt(2, IdUsuario);
                        callableStatement.execute();
                        ResultSet resultSet = (ResultSet) callableStatement.getObject(1);

                        while (resultSet.next()) {
                            if (!result.objects.isEmpty() && IdUsuario == ((Usuario) (result.objects.get(result.objects.size() - 1))).getIdUsuario()) {
                                Direccion direccion = new Direccion();
                                direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                                direccion.setCalle(resultSet.getString("Calle"));
                                direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                                direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                                direccion.Colonia = new Colonia();
                                direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                                direccion.Colonia.setNombre(resultSet.getString("Colonia"));
                                direccion.Colonia.setCodigoPostal(resultSet.getString("CP"));
                                direccion.Colonia.Municipio = new Municipio();
                                direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                                direccion.Colonia.Municipio.setNombre(resultSet.getString("Municipio"));
                                direccion.Colonia.Municipio.Estado = new Estado();
                                direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                                direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("Estado"));
                                direccion.Colonia.Municipio.Estado.Pais = new Pais();
                                direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                                direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("Pais"));
                                ((Usuario) (result.objects.get(result.objects.size() - 1))).Direcciones.add(direccion);
                            } else {
                                Usuario usuario = new Usuario();
                                usuario.Rol = new Rol();
                                usuario.Direcciones = new ArrayList();
                                usuario.setIdUsuario(IdUsuario);
                                usuario.setNombre(resultSet.getString("NombreUsuario"));
                                usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                                usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                                usuario.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
                                usuario.setCelular(resultSet.getString("Celular"));
                                usuario.setCurp(resultSet.getString("Curp"));
                                usuario.setUserName(resultSet.getString("Usuario"));
                                usuario.setEmail(resultSet.getString("Email"));
                                usuario.setPassword(resultSet.getString("Contraseña"));
                                usuario.setSexo(resultSet.getString("Sexo"));
                                usuario.setTelefono(resultSet.getString("Telefono"));
                                usuario.setEstatus(resultSet.getInt("Estatus"));
                                usuario.Rol.setNombre(resultSet.getString("RolAsignado"));
                                Direccion direccion = new Direccion();
                                direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                                direccion.setCalle(resultSet.getString("Calle"));
                                direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                                direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                                direccion.Colonia = new Colonia();
                                direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                                direccion.Colonia.setNombre(resultSet.getString("Colonia"));
                                direccion.Colonia.setCodigoPostal(resultSet.getString("CP"));
                                direccion.Colonia.Municipio = new Municipio();
                                direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                                direccion.Colonia.Municipio.setNombre(resultSet.getString("Municipio"));
                                direccion.Colonia.Municipio.Estado = new Estado();
                                direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                                direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("Estado"));
                                direccion.Colonia.Municipio.Estado.Pais = new Pais();
                                direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("Pais"));
                                direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("Pais"));
                                usuario.Direcciones.add(direccion);
                                result.objects.add(usuario);
                            }
                        }
                        return result.correct;
                    }
            );
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }

    @Override
    public Result DeleteDireccionUsuariobyId(int IdUsuario) {
        Result result = new Result();
        try {
            jdbcTemplate.execute("{CALL deletedireccionusuariosp(?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.setInt(1, IdUsuario);
                callableStatement.execute();
                return result.correct = true;
            });
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;

        }
        return result;
    }

    @Override
    public Result DeleteDireccionById(int IdDireccion) {
        Result result = new Result();

        try {
            jdbcTemplate.execute("CALL deletedireccionbyidsp(?)", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.setInt(1, IdDireccion);
                callableStatement.execute();
                return result.correct = true;
            });

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }

    @Override
    public Result Add(Usuario usuario) {
        Result result = new Result();
        try {
            jdbcTemplate.execute("{CALL usuariodireccionaddsp(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.setString(1, usuario.getNombre());
                callableStatement.setString(2, usuario.getApellidoPaterno());
                callableStatement.setString(3, usuario.getApellidoMaterno());
                callableStatement.setDate(4, new java.sql.Date(usuario.getFechaNacimiento().getTime()));
                callableStatement.setString(5, usuario.getCelular());
                callableStatement.setString(6, usuario.getCurp());
                callableStatement.setString(7, usuario.getUserName());
                callableStatement.setString(8, usuario.getEmail());
                callableStatement.setString(9, usuario.getPassword());
                callableStatement.setString(10, usuario.getSexo());
                callableStatement.setString(11, usuario.getTelefono());
                callableStatement.setInt(12, usuario.getRol().getIdRol());

                Direccion direccion = usuario.getDirecciones().get(0);

                callableStatement.setString(13, direccion.getCalle());
                callableStatement.setString(14, direccion.getNumeroExterior());
                callableStatement.setString(15, direccion.getNumeroInterior());
                callableStatement.setInt(16, direccion.getColonia().getIdColonia());

                callableStatement.execute();

                return result.correct = true;
            });
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }

    @Override
    public Result UsuarioDireccionBusqueda(Usuario usuario) {
        Result result = new Result();
        result.objects = new ArrayList<>();

        try {

            jdbcTemplate.execute("{CALL usuariodireccionbusquedasp(?,?,?,?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                String nombre = (usuario.getNombre() != null && !usuario.getNombre().isEmpty()) ? usuario.getNombre() : null;
                String apellidoPaterno = (usuario.getApellidoPaterno() != null && !usuario.getApellidoPaterno().isEmpty()) ? usuario.getApellidoPaterno() : null;
                String apellidoMaterno = (usuario.getApellidoMaterno() != null && !usuario.getApellidoMaterno().isEmpty()) ? usuario.getApellidoMaterno() : null;
                int idRol = (usuario.getRol() != null) ? usuario.getRol().getIdRol() : 0;
                callableStatement.setString(1, nombre);
                callableStatement.setString(2, apellidoPaterno);
                callableStatement.setString(3, apellidoMaterno);
                callableStatement.setInt(4, idRol);
                callableStatement.registerOutParameter(5, java.sql.Types.REF_CURSOR);
                callableStatement.execute();
                ResultSet resultSet = (ResultSet) callableStatement.getObject(5);
                while (resultSet.next()) {
                    int IdUsuario = resultSet.getInt("IdUsuario");
                    if (!result.objects.isEmpty() && IdUsuario == ((Usuario) (result.objects.get(result.objects.size() - 1))).getIdUsuario()) {
                        Direccion direccion = new Direccion();
                        direccion.setIdColonia(resultSet.getInt("IdColonia"));
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.Colonia = new Colonia();
                        direccion.Colonia.setNombre(resultSet.getString("Colonia"));
                        direccion.Colonia.setCodigoPostal(resultSet.getString("CP"));
                        direccion.Colonia.Municipio = new Municipio();
                        direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                        direccion.Colonia.Municipio.setNombre(resultSet.getString("Municipio"));
                        direccion.Colonia.Municipio.Estado = new Estado();
                        direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                        direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("Estado"));
                        direccion.Colonia.Municipio.Estado.Pais = new Pais();
                        direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                        direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("Pais"));
                        ((Usuario) (result.objects.get(result.objects.size() - 1))).Direcciones.add(direccion);
                    } else {
                        Usuario usuarioFila = new Usuario();
                        usuarioFila.Rol = new Rol();
                        usuarioFila.Direcciones = new ArrayList();
                        usuarioFila.setIdUsuario(IdUsuario);
                        usuarioFila.setNombre(resultSet.getString("NombreUsuario"));
                        usuarioFila.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                        usuarioFila.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                        usuarioFila.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
                        usuarioFila.setCelular(resultSet.getString("Celular"));
                        usuarioFila.setCurp(resultSet.getString("Curp"));
                        usuarioFila.setUserName(resultSet.getString("Usuario"));
                        usuarioFila.setEmail(resultSet.getString("Correo"));
                        usuarioFila.setPassword(resultSet.getString("Contraseña"));
                        usuarioFila.setSexo(resultSet.getString("Sexo"));
                        usuarioFila.setTelefono(resultSet.getString("Telefono"));
                        usuarioFila.Rol.setNombre(resultSet.getString("RolAsignado"));
                        Direccion direccion = new Direccion();
                        direccion.setIdColonia(resultSet.getInt("IdColonia"));
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.Colonia = new Colonia();
                        direccion.Colonia.setNombre(resultSet.getString("Colonia"));
                        direccion.Colonia.setCodigoPostal(resultSet.getString("CP"));
                        direccion.Colonia.Municipio = new Municipio();
                        direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                        direccion.Colonia.Municipio.setNombre(resultSet.getString("Municipio"));
                        direccion.Colonia.Municipio.Estado = new Estado();
                        direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                        direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("Estado"));
                        direccion.Colonia.Municipio.Estado.Pais = new Pais();
                        direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                        direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("Pais"));
                        usuarioFila.Direcciones.add(direccion);
                        usuarioFila.setEstatus(resultSet.getInt("Estatus"));
                        result.objects.add(usuarioFila);
                    }
                }
                return result.correct = true;
            });
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }

    @Override
    public Result ModifyUsuario(Usuario usuario) {
        Result result = new Result();

        try {
            jdbcTemplate.execute("{CALL UsuarioModifySP(?,?,?,?,?,?,?,?,?,?,?,?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.setString(1, usuario.getNombre());
                callableStatement.setString(2, usuario.getApellidoPaterno());
                callableStatement.setString(3, usuario.getApellidoMaterno());
                callableStatement.setString(4, usuario.getCelular());
                callableStatement.setString(5, usuario.getCurp());
                callableStatement.setString(6, usuario.getUserName());
                callableStatement.setString(7, usuario.getEmail());
                callableStatement.setString(8, usuario.getPassword());
                callableStatement.setString(9, usuario.getSexo());
                callableStatement.setString(10, usuario.getTelefono());
                callableStatement.setDate(11, new java.sql.Date(usuario.getFechaNacimiento().getTime()));
                callableStatement.setInt(12, usuario.getRol().getIdRol());
                callableStatement.setInt(13, usuario.getIdUsuario());
                callableStatement.execute();

                return result.correct = true;
            });
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }

    @Override
    public Result CambiarEstatus(int IdUsuario, int Estatus) {
        Result result = new Result();
        try {
            jdbcTemplate.execute("{Call cambiarestatussp(?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.setInt(1, IdUsuario);
                callableStatement.setInt(2, Estatus);
                callableStatement.executeUpdate();

                return result.correct = true;
            });
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result AddAll(List<Usuario> usuarios) {
        Result result = new Result();
        try {
            String sql = "{CALL usuariodireccionaddsp(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            int tamano = usuarios.size();
            jdbcTemplate.batchUpdate(sql, usuarios, tamano, (callableStatement, usuario) -> {
                callableStatement.setString(1, usuario.getNombre());
                callableStatement.setString(2, usuario.getApellidoPaterno());
                callableStatement.setString(3, usuario.getApellidoMaterno());
                callableStatement.setDate(4, new java.sql.Date(usuario.getFechaNacimiento().getTime()));
                callableStatement.setString(5, usuario.getCelular());
                callableStatement.setString(6, usuario.getCurp());
                callableStatement.setString(7, usuario.getUserName());
                callableStatement.setString(8, usuario.getEmail());
                callableStatement.setString(9, usuario.getPassword());
                callableStatement.setString(10, usuario.getSexo());
                callableStatement.setString(11, usuario.getTelefono());
                callableStatement.setInt(12, usuario.getRol().getIdRol());

                Direccion direccion = usuario.getDirecciones().get(0);

                callableStatement.setString(13, direccion.getCalle());
                callableStatement.setString(14, direccion.getNumeroExterior());
                callableStatement.setString(15, direccion.getNumeroInterior());
                callableStatement.setInt(16, direccion.getColonia().getIdColonia());
            });
            result.correct = true;
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;

    }
}
