package cl.duoc.cloudnative.cursos.dto;

public record ProcesarColaResponse(
        int mensajesProcesados,
        int errores,
        String detalle
) {
}
