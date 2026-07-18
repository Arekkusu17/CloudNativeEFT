package cl.duoc.cloudnative.cursos.service;

import cl.duoc.cloudnative.cursos.dto.CalificacionResponse;
import cl.duoc.cloudnative.cursos.dto.CrearExamenRequest;
import cl.duoc.cloudnative.cursos.dto.EvaluacionMessage;
import cl.duoc.cloudnative.cursos.dto.ExamenResponse;
import cl.duoc.cloudnative.cursos.dto.RendirExamenRequest;
import cl.duoc.cloudnative.cursos.model.Calificacion;
import cl.duoc.cloudnative.cursos.model.Curso;
import cl.duoc.cloudnative.cursos.model.EstadoCalificacion;
import cl.duoc.cloudnative.cursos.model.EstadoExamen;
import cl.duoc.cloudnative.cursos.model.Examen;
import cl.duoc.cloudnative.cursos.repository.CalificacionRepository;
import cl.duoc.cloudnative.cursos.repository.ExamenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ExamenService {

    private final CursoService cursoService;
    private final ExamenRepository examenRepository;
    private final CalificacionRepository calificacionRepository;
    private final EvaluacionProducer producer;

    public ExamenService(
            CursoService cursoService,
            ExamenRepository examenRepository,
            CalificacionRepository calificacionRepository,
            EvaluacionProducer producer
    ) {
        this.cursoService = cursoService;
        this.examenRepository = examenRepository;
        this.calificacionRepository = calificacionRepository;
        this.producer = producer;
    }

    @Transactional
    public ExamenResponse crearExamen(CrearExamenRequest request) {
        Curso curso = cursoService.obtenerCurso(request.cursoId());
        Examen examen = new Examen(
                UUID.randomUUID(),
                curso,
                request.titulo(),
                request.puntajeMaximo(),
                EstadoExamen.DISPONIBLE
        );
        return toResponse(examenRepository.save(examen));
    }

    @Transactional(readOnly = true)
    public List<ExamenResponse> listarPorCurso(UUID cursoId) {
        return examenRepository.findByCursoId(cursoId).stream().map(this::toResponse).toList();
    }

    @Transactional
    public CalificacionResponse rendirExamen(UUID examenId, RendirExamenRequest request) {
        Examen examen = obtenerExamen(examenId);
        if (request.puntaje() > examen.getPuntajeMaximo()) {
            throw new IllegalArgumentException("El puntaje no puede superar el maximo del examen");
        }

        Calificacion calificacion = new Calificacion(
                UUID.randomUUID(),
                examen,
                request.estudiante(),
                request.puntaje(),
                EstadoCalificacion.PENDIENTE,
                LocalDateTime.now()
        );
        Calificacion guardada = calificacionRepository.save(calificacion);

        producer.publicarEvaluacion(new EvaluacionMessage(
                guardada.getId(),
                examen.getId(),
                guardada.getEstudiante(),
                guardada.getPuntaje(),
                guardada.getFechaCalificacion()
        ));

        return toResponse(guardada);
    }

    @Transactional(readOnly = true)
    public List<CalificacionResponse> listarCalificaciones(UUID examenId) {
        return calificacionRepository.findByExamenId(examenId).stream().map(this::toResponse).toList();
    }

    public Examen obtenerExamen(UUID id) {
        return examenRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Examen no encontrado: " + id));
    }

    private ExamenResponse toResponse(Examen examen) {
        return new ExamenResponse(
                examen.getId(),
                examen.getCurso().getId(),
                examen.getTitulo(),
                examen.getPuntajeMaximo(),
                examen.getEstado()
        );
    }

    private CalificacionResponse toResponse(Calificacion calificacion) {
        return new CalificacionResponse(
                calificacion.getId(),
                calificacion.getExamen().getId(),
                calificacion.getEstudiante(),
                calificacion.getPuntaje(),
                calificacion.getEstado(),
                calificacion.getFechaCalificacion()
        );
    }
}
