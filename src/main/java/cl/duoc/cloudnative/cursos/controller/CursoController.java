package cl.duoc.cloudnative.cursos.controller;

import cl.duoc.cloudnative.cursos.dto.ActualizarCursoRequest;
import cl.duoc.cloudnative.cursos.dto.ArchivoContenido;
import cl.duoc.cloudnative.cursos.dto.ContenidoResponse;
import cl.duoc.cloudnative.cursos.dto.CrearCursoRequest;
import cl.duoc.cloudnative.cursos.dto.CursoResponse;
import cl.duoc.cloudnative.cursos.service.CursoService;
import jakarta.validation.Valid;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    private final CursoService service;

    public CursoController(CursoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CursoResponse crear(@Valid @RequestBody CrearCursoRequest request) {
        return service.crearCurso(request);
    }

    @GetMapping
    public ResponseEntity<List<CursoResponse>> listar(@RequestParam(required = false) String instructor) {
        return ResponseEntity.ok(service.listar(instructor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoResponse> actualizar(@PathVariable UUID id, @Valid @RequestBody ActualizarCursoRequest request) {
        return ResponseEntity.ok(service.actualizarCurso(id, request));
    }

    @PostMapping("/{id}/contenidos/demo")
    public ResponseEntity<ContenidoResponse> generarMaterialDemo(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.generarMaterialDemo(id));
    }

    @GetMapping("/{id}/contenidos")
    public ResponseEntity<List<ContenidoResponse>> listarContenidos(@PathVariable UUID id) {
        return ResponseEntity.ok(service.listarContenidos(id));
    }

    @GetMapping("/contenidos/{contenidoId}/descargar")
    public ResponseEntity<byte[]> descargarContenido(@PathVariable UUID contenidoId) {
        ArchivoContenido archivo = service.descargarContenido(contenidoId);
        MediaType mediaType = archivo.contentType() == null
                ? MediaType.APPLICATION_OCTET_STREAM
                : MediaType.parseMediaType(archivo.contentType());

        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment()
                        .filename(archivo.nombreArchivo())
                        .build()
                        .toString())
                .body(archivo.contenido());
    }
}
