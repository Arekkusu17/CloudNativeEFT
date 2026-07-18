package cl.duoc.cloudnative.cursos.repository;

import cl.duoc.cloudnative.cursos.model.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface InscripcionRepository extends JpaRepository<Inscripcion, UUID> {

    List<Inscripcion> findByCursoId(UUID cursoId);
}
