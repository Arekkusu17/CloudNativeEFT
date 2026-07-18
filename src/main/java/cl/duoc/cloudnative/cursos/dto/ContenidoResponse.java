package cl.duoc.cloudnative.cursos.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ContenidoResponse(
        UUID id,
        UUID cursoId,
        String titulo,
        String nombreArchivo,
        String contentType,
        String s3Key,
        LocalDateTime fechaCarga
) {
}
