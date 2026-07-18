package cl.duoc.cloudnative.cursos.service;

import cl.duoc.cloudnative.cursos.dto.CrearInscripcionRequest;
import cl.duoc.cloudnative.cursos.dto.InscripcionResponse;
import cl.duoc.cloudnative.cursos.model.Curso;
import cl.duoc.cloudnative.cursos.model.EstadoInscripcion;
import cl.duoc.cloudnative.cursos.model.Inscripcion;
import cl.duoc.cloudnative.cursos.repository.InscripcionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class InscripcionService {

    private final CursoService cursoService;
    private final InscripcionRepository repository;

    public InscripcionService(CursoService cursoService, InscripcionRepository repository) {
        this.cursoService = cursoService;
        this.repository = repository;
    }

    @Transactional
    public InscripcionResponse inscribir(CrearInscripcionRequest request) {
        Curso curso = cursoService.obtenerCurso(request.cursoId());
        Inscripcion inscripcion = new Inscripcion(
                UUID.randomUUID(),
                curso,
                request.estudiante(),
                request.email(),
                EstadoInscripcion.ACTIVA,
                LocalDate.now()
        );
        return toResponse(repository.save(inscripcion));
    }

    @Transactional(readOnly = true)
    public List<InscripcionResponse> listarPorCurso(UUID cursoId) {
        return repository.findByCursoId(cursoId).stream().map(this::toResponse).toList();
    }

    private InscripcionResponse toResponse(Inscripcion inscripcion) {
        return new InscripcionResponse(
                inscripcion.getId(),
                inscripcion.getCurso().getId(),
                inscripcion.getEstudiante(),
                inscripcion.getEmail(),
                inscripcion.getEstado(),
                inscripcion.getFechaInscripcion()
        );
    }
}
