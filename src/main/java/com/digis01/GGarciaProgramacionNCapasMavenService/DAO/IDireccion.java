
package com.digis01.GGarciaProgramacionNCapasMavenService.DAO;

import com.digis01.GGarciaProgramacionNCapasMavenService.JPA.Result;
import com.digis01.GGarciaProgramacionNCapasMavenService.JPA.Direccion;

public interface IDireccion {

    Result DireccionGetAllById(int IdUsuario);

    Result DireccionAdd(Direccion direccion, int IdUsuario);

    Result DireccionModify(Direccion direccion, int IdUsuario);
}
