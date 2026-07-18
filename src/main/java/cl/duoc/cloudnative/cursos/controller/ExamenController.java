package cl.duoc.cloudnative.cursos.controller;

import cl.duoc.cloudnative.cursos.dto.CalificacionResponse;
import cl.duoc.cloudnative.cursos.dto.CrearExamenRequest;
import cl.duoc.cloudnative.cursos.dto.ExamenResponse;
import cl.duoc.cloudnative.cursos.dto.RendirExamenRequest;
import cl.duoc.cloudnative.cursos.service.ExamenService;
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
@RequestMapping("/api/examenes")
public class ExamenController {

    private final ExamenService service;

    public ExamenController(ExamenService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExamenResponse crear(@Valid @RequestBody CrearExamenRequest request) {
        return service.crearExamen(request);
    }

    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<List<ExamenResponse>> listarPorCurso(@PathVariable UUID cursoId) {
        return ResponseEntity.ok(service.listarPorCurso(cursoId));
    }

    @PostMapping("/{id}/rendir")
    public ResponseEntity<CalificacionResponse> rendir(@PathVariable UUID id, @Valid @RequestBody RendirExamenRequest request) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.rendirExamen(id, request));
    }

    @GetMapping("/{id}/calificaciones")
    public ResponseEntity<List<CalificacionResponse>> listarCalificaciones(@PathVariable UUID id) {
        return ResponseEntity.ok(service.listarCalificaciones(id));
    }
}
