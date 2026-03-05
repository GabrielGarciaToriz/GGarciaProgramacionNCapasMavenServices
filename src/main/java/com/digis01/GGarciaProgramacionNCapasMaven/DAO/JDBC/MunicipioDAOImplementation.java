package com.digis01.GGarciaProgramacionNCapasMaven.DAO.JDBC;

import com.digis01.GGarciaProgramacionNCapasMaven.DAO.IMunicipio;
import com.digis01.GGarciaProgramacionNCapasMaven.ML.Municipio;
import com.digis01.GGarciaProgramacionNCapasMaven.ML.Estado;
import com.digis01.GGarciaProgramacionNCapasMaven.ML.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MunicipioDAOImplementation implements IMunicipio {

    @Autowired
    private JdbcTemplate JdbcTemplate;

    @Override
    public Result GetAll(int IdEstado) {
        Result result = new Result();
        result.objects = new ArrayList<>();

        try {
            JdbcTemplate.execute("CALL estadomunicipiobyid(?,?)", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatement.setInt(2, IdEstado);
                callableStatement.execute();
                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                while (resultSet.next()) {
                    Municipio municipio = new Municipio();
                    municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                    municipio.setNombre(resultSet.getString("Municipio"));

                    Estado estado = new Estado();
                    estado.setIdEstado(resultSet.getInt("IdEstado"));
                    municipio.setEstado(estado);
                    result.objects.add(municipio);
                }
                return result.correct;

            }
            );
        } catch (Exception e) {
            result.errorMessage = e.getLocalizedMessage();
            result.correct = false;
            result.ex = e;
        }

        return result;
    }

}
