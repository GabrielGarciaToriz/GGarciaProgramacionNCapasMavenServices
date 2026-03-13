package com.digis01.GGarciaProgramacionNCapasMavenService.DAO;

import com.digis01.GGarciaProgramacionNCapasMavenService.Entity.Result;

public interface IColonia {

    Result GetAllById(int IdMunicipio);
    Result GetByCodigoPostal(String CodigoPostal);
}
