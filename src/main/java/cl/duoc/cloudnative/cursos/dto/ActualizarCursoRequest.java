package cl.duoc.cloudnative.cursos.dto;

import cl.duoc.cloudnative.cursos.model.EstadoCurso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ActualizarCursoRequest(
        @NotBlank @Size(max = 120) String titulo,
        @NotBlank @Size(max = 500) String descripcion,
        @NotBlank @Size(max = 120) String instructor,
        @NotNull EstadoCurso estado
) {
}
