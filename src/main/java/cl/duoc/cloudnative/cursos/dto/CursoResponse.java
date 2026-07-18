package cl.duoc.cloudnative.cursos.dto;

import cl.duoc.cloudnative.cursos.model.EstadoCurso;

import java.time.LocalDate;
import java.util.UUID;

public record CursoResponse(
        UUID id,
        String titulo,
        String descripcion,
        String instructor,
        EstadoCurso estado,
        LocalDate fechaCreacion
) {
}
