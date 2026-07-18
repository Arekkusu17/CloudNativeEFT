package cl.duoc.cloudnative.cursos.controller;

import cl.duoc.cloudnative.cursos.dto.CalificacionResponse;
import cl.duoc.cloudnative.cursos.dto.ProcesarColaResponse;
import cl.duoc.cloudnative.cursos.dto.RendirExamenRequest;
import cl.duoc.cloudnative.cursos.service.EvaluacionQueueService;
import cl.duoc.cloudnative.cursos.service.ExamenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/bff")
public class BffController {

    private final ExamenService examenService;
    private final EvaluacionQueueService queueService;

    public BffController(ExamenService examenService, EvaluacionQueueService queueService) {
        this.examenService = examenService;
        this.queueService = queueService;
    }

    @PostMapping("/examenes/{id}/rendir")
    public ResponseEntity<CalificacionResponse> rendirExamen(@PathVariable UUID id, @Valid @RequestBody RendirExamenRequest request) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(examenService.rendirExamen(id, request));
    }

    @PostMapping("/colas/evaluaciones/procesar")
    public ResponseEntity<ProcesarColaResponse> procesarEvaluaciones(@RequestParam(defaultValue = "10") int limite) {
        return ResponseEntity.ok(queueService.procesarPendientes(limite));
    }
}
