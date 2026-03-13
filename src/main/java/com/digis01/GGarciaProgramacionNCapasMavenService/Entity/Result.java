package com.digis01.GGarciaProgramacionNCapasMavenService.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Objeto generico que encapsula todas las respuestas de la API, estandarizando la fomra en la que el cliente recibe los datos")
public class Result {

    @Schema(description = "Indica si la operación en el servidor fue exitosa (true) o si ocurrió una validación/error (false)", example = "true")
    public boolean correct;

    @Schema(description = "Mensaje descriptivo del error en caso de que 'correct' sea false. Si es exitoso, suele ser null o vacío.", example = "El usuario no fue encontrado en la base de datos", nullable = true)
    public String errorMessage;

    @Schema(description = "Excepción técnica interna. Generalmente oculta para el cliente.", hidden = true)
    public Exception ex;

    @Schema(description = "Contiene un único objeto resultante de la operación (por ejemplo, al buscar por ID).", nullable = true)
    public Object object;

    @Schema(description = "Contiene una lista de objetos resultantes de la operación (por ejemplo, al pedir un catálogo completo).", nullable = true)
    public List<Object> objects;
}
