package cl.duoc.cloudnative.cursos.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CrearExamenRequest(
        @NotNull UUID cursoId,
        @NotBlank String titulo,
        @Min(1) @Max(100) Integer puntajeMaximo
) {
}
