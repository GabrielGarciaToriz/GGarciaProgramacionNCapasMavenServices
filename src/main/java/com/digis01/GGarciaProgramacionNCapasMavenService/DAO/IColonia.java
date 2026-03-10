package com.digis01.GGarciaProgramacionNCapasMavenService.DAO;

import com.digis01.GGarciaProgramacionNCapasMavenService.JPA.Result;

public interface IColonia {

    Result GetAllById(int IdMunicipio);
    Result GetByCodigoPostal(String CodigoPostal);
}
