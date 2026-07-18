package cl.duoc.cloudnative.cursos.dto;

import cl.duoc.cloudnative.cursos.model.EstadoExamen;

import java.util.UUID;

public record ExamenResponse(
        UUID id,
        UUID cursoId,
        String titulo,
        Integer puntajeMaximo,
        EstadoExamen estado
) {
}
