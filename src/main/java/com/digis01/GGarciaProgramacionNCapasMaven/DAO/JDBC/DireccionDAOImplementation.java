package com.digis01.GGarciaProgramacionNCapasMaven.DAO.JDBC;

import com.digis01.GGarciaProgramacionNCapasMaven.DAO.IDireccion;
import com.digis01.GGarciaProgramacionNCapasMaven.ML.Result;
import com.digis01.GGarciaProgramacionNCapasMaven.ML.Direccion;
import java.sql.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository //PORQUE TIENE ACCESO A LA BASE DE DATOS
public class DireccionDAOImplementation implements IDireccion {

    @Autowired
    private JdbcTemplate JdbcTemplate;

    @Override
    public Result DireccionGetAllById(int IdDireccion) {
        Result result = new Result();
        try {
            JdbcTemplate.execute("{CALL direcciongetbyid(?,?)}", (CallableStatementCallback< Boolean>) callableStatement -> {
                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatement.setInt(2, IdDireccion);
                callableStatement.execute();
                ResultSet resultSet = (ResultSet) callableStatement.getObject(0);
                while (resultSet.next()) {
                    Direccion direccion = new Direccion();
                    direccion.setIdDireccion(IdDireccion);
                    direccion.setCalle(resultSet.getString("Calle"));
                    direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                    direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                    direccion.Colonia.setIdColonia(resultSet.getInt("IdClonia"));
                    result.objects.add(direccion);
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
    public Result DireccionAdd(Direccion direccion, int IdUsuario) {
        Result result = new Result();
        try {
            JdbcTemplate.execute("{CALL DIREECIONADDSP(?,?,?,?,?)}", (CallableStatementCallback< Boolean>) callableStatement -> {
                callableStatement.setString(1, direccion.getCalle());
                callableStatement.setString(2, direccion.getNumeroExterior());
                callableStatement.setString(3, direccion.getNumeroInterior());
                callableStatement.setInt(4, direccion.getColonia().getIdColonia());
                callableStatement.setInt(5, IdUsuario);
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
    public Result DireccionModify(Direccion direccion, int IdUsuario) {
        Result result = new Result();
        try {
            JdbcTemplate.execute("{CALL modifydireccionsp(?,?,?,?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.setString(1, direccion.getCalle());
                callableStatement.setString(2, direccion.getNumeroExterior());
                callableStatement.setString(3, direccion.getNumeroInterior());
                callableStatement.setInt(4, direccion.getColonia().getIdColonia());
                callableStatement.setInt(5, IdUsuario);
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
}
