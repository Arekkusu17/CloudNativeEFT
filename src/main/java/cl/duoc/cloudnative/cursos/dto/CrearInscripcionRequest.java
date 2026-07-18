package cl.duoc.cloudnative.cursos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CrearInscripcionRequest(
        @NotNull UUID cursoId,
        @NotBlank String estudiante,
        @NotBlank @Email String email
) {
}
