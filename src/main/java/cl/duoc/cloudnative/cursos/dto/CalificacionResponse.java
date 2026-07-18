package cl.duoc.cloudnative.cursos.dto;

import cl.duoc.cloudnative.cursos.model.EstadoCalificacion;

import java.time.LocalDateTime;
import java.util.UUID;

public record CalificacionResponse(
        UUID id,
        UUID examenId,
        String estudiante,
        Integer puntaje,
        EstadoCalificacion estado,
        LocalDateTime fechaCalificacion
) {
}
