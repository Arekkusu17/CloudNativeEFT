package cl.duoc.cloudnative.cursos.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record EvaluacionMessage(
        UUID calificacionId,
        UUID examenId,
        String estudiante,
        Integer puntaje,
        LocalDateTime fechaRegistro
) {
}
