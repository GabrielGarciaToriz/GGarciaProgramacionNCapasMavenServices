package com.digis01.GGarciaProgramacionNCapasMaven.DAO;

import com.digis01.GGarciaProgramacionNCapasMaven.JPA.Result;

public interface IColonia {

    Result GetAllById(int IdMunicipio);
    Result GetByCodigoPostal(String CodigoPostal);
}
