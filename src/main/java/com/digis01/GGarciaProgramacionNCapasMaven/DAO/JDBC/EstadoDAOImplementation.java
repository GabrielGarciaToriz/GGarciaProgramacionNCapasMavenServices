package com.digis01.GGarciaProgramacionNCapasMaven.DAO.JDBC;

import com.digis01.GGarciaProgramacionNCapasMaven.DAO.IEstado;
import com.digis01.GGarciaProgramacionNCapasMaven.ML.Result;
import com.digis01.GGarciaProgramacionNCapasMaven.ML.Estado;
import com.digis01.GGarciaProgramacionNCapasMaven.ML.Pais;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EstadoDAOImplementation implements IEstado {

    @Autowired
    private JdbcTemplate JdbcTemplate;

    @Override
    public Result GetAll(int IdPais) {
        Result result = new Result();
        result.objects = new ArrayList<>();
        try {
            JdbcTemplate.execute("{CALL paisestadobyidsp(?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatement.setInt(2, IdPais);
                callableStatement.execute();
                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);

                while (resultSet.next()) {
                    Estado estado = new Estado();
                    estado.setIdEstado(resultSet.getInt("IdEstado"));
                    estado.setNombre(resultSet.getString("Estado"));

                    Pais pais = new Pais();
                    pais.setIdPais(resultSet.getInt("IdPais"));
                    estado.setPais(pais);
                    result.objects.add(estado);
                }

                return result.correct;

            });
        } catch (Exception e) {
            result.errorMessage = e.getLocalizedMessage();
            result.correct = false;
            result.ex = e;
        }
        return result;
    }

    @Override
    public Result GetAll() {
        return null;
    }

}
