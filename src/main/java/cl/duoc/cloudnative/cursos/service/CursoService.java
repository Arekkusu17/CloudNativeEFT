package cl.duoc.cloudnative.cursos.service;

import cl.duoc.cloudnative.cursos.dto.ActualizarCursoRequest;
import cl.duoc.cloudnative.cursos.dto.ArchivoContenido;
import cl.duoc.cloudnative.cursos.dto.ContenidoResponse;
import cl.duoc.cloudnative.cursos.dto.CrearCursoRequest;
import cl.duoc.cloudnative.cursos.dto.CursoResponse;
import cl.duoc.cloudnative.cursos.model.ContenidoCurso;
import cl.duoc.cloudnative.cursos.model.Curso;
import cl.duoc.cloudnative.cursos.model.EstadoCurso;
import cl.duoc.cloudnative.cursos.repository.ContenidoCursoRepository;
import cl.duoc.cloudnative.cursos.repository.CursoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;
    private final ContenidoCursoRepository contenidoRepository;
    private final StorageService storageService;

    public CursoService(CursoRepository cursoRepository, ContenidoCursoRepository contenidoRepository, StorageService storageService) {
        this.cursoRepository = cursoRepository;
        this.contenidoRepository = contenidoRepository;
        this.storageService = storageService;
    }

    @Transactional
    public CursoResponse crearCurso(CrearCursoRequest request) {
        Curso curso = new Curso(
                UUID.randomUUID(),
                request.titulo(),
                request.descripcion(),
                request.instructor(),
                EstadoCurso.PUBLICADO,
                LocalDate.now()
        );
        return toResponse(cursoRepository.save(curso));
    }

    @Transactional(readOnly = true)
    public List<CursoResponse> listar(String instructor) {
        List<Curso> cursos = instructor == null || instructor.isBlank()
                ? cursoRepository.findAll()
                : cursoRepository.findByInstructorContainingIgnoreCase(instructor);
        return cursos.stream().map(this::toResponse).toList();
    }

    @Transactional
    public CursoResponse actualizarCurso(UUID id, ActualizarCursoRequest request) {
        Curso curso = obtenerCurso(id);
        curso.actualizar(request.titulo(), request.descripcion(), request.instructor(), request.estado());
        return toResponse(cursoRepository.save(curso));
    }

    @Transactional
    public ContenidoResponse generarMaterialDemo(UUID cursoId) {
        Curso curso = obtenerCurso(cursoId);
        UUID contenidoId = UUID.randomUUID();
        String nombreArchivo = "material-" + cursoId + ".txt";
        String key = "cursos/" + cursoId + "/" + nombreArchivo;
        String texto = "Material de apoyo para el curso " + curso.getTitulo()
                + System.lineSeparator()
                + "Instructor: " + curso.getInstructor();

        storageService.subir(key, "text/plain", texto.getBytes(StandardCharsets.UTF_8));

        ContenidoCurso contenido = new ContenidoCurso(
                contenidoId,
                curso,
                "Material introductorio",
                nombreArchivo,
                "text/plain",
                key,
                LocalDateTime.now()
        );
        return toResponse(contenidoRepository.save(contenido));
    }

    @Transactional(readOnly = true)
    public ArchivoContenido descargarContenido(UUID contenidoId) {
        ContenidoCurso contenido = contenidoRepository.findById(contenidoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Contenido no encontrado: " + contenidoId));
        return storageService.descargar(contenido.getS3Key(), contenido.getNombreArchivo(), contenido.getContentType());
    }

    @Transactional(readOnly = true)
    public List<ContenidoResponse> listarContenidos(UUID cursoId) {
        return contenidoRepository.findByCursoId(cursoId).stream().map(this::toResponse).toList();
    }

    public Curso obtenerCurso(UUID id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Curso no encontrado: " + id));
    }

    private CursoResponse toResponse(Curso curso) {
        return new CursoResponse(
                curso.getId(),
                curso.getTitulo(),
                curso.getDescripcion(),
                curso.getInstructor(),
                curso.getEstado(),
                curso.getFechaCreacion()
        );
    }

    private ContenidoResponse toResponse(ContenidoCurso contenido) {
        return new ContenidoResponse(
                contenido.getId(),
                contenido.getCurso().getId(),
                contenido.getTitulo(),
                contenido.getNombreArchivo(),
                contenido.getContentType(),
                contenido.getS3Key(),
                contenido.getFechaCarga()
        );
    }
}
