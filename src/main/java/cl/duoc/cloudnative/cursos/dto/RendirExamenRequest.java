package cl.duoc.cloudnative.cursos.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RendirExamenRequest(
        @NotBlank String estudiante,
        @NotNull @Min(0) @Max(100) Integer puntaje
) {
}
