package cl.duoc.cloudnative.cursos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CrearCursoRequest(
        @NotBlank @Size(max = 120) String titulo,
        @NotBlank @Size(max = 500) String descripcion,
        @NotBlank @Size(max = 120) String instructor
) {
}
