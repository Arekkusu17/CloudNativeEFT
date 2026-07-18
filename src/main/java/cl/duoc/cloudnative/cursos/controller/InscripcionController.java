package cl.duoc.cloudnative.cursos.controller;

import cl.duoc.cloudnative.cursos.dto.CrearInscripcionRequest;
import cl.duoc.cloudnative.cursos.dto.InscripcionResponse;
import cl.duoc.cloudnative.cursos.service.InscripcionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController {

    private final InscripcionService service;

    public InscripcionController(InscripcionService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InscripcionResponse inscribir(@Valid @RequestBody CrearInscripcionRequest request) {
        return service.inscribir(request);
    }

    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<List<InscripcionResponse>> listarPorCurso(@PathVariable UUID cursoId) {
        return ResponseEntity.ok(service.listarPorCurso(cursoId));
    }
}
