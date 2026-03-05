package com.digis01.GGarciaProgramacionNCapasMaven.DAO;

import com.digis01.GGarciaProgramacionNCapasMaven.ML.Result;

public interface IColonia {

    Result GetAll(int IdMunicipio);
    Result GetByCodigoPostal(String CodigoPostal);
}
