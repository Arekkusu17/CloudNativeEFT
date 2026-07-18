package cl.duoc.cloudnative.cursos.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record EvaluacionErrorMessage(
        UUID calificacionId,
        UUID examenId,
        String estudiante,
        String motivo,
        LocalDateTime fechaError
) {
}
