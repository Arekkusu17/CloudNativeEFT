package cl.duoc.cloudnative.cursos.dto;

public record ArchivoContenido(
        String nombreArchivo,
        String contentType,
        byte[] contenido
) {
}
