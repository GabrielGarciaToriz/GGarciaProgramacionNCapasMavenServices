
package com.digis01.GGarciaProgramacionNCapasMaven.DAO;

import com.digis01.GGarciaProgramacionNCapasMaven.JPA.Result;
import com.digis01.GGarciaProgramacionNCapasMaven.JPA.Direccion;

public interface IDireccion {

    Result DireccionGetAllById(int IdDireccion);

    Result DireccionAdd(Direccion direccion, int IdUsuario);

    Result DireccionModify(Direccion direccion, int IdUsuario);
}
