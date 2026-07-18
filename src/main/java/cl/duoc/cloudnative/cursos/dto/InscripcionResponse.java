package cl.duoc.cloudnative.cursos.dto;

import cl.duoc.cloudnative.cursos.model.EstadoInscripcion;

import java.time.LocalDate;
import java.util.UUID;

public record InscripcionResponse(
        UUID id,
        UUID cursoId,
        String estudiante,
        String email,
        EstadoInscripcion estado,
        LocalDate fechaInscripcion
) {
}
