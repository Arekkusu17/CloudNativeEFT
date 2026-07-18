package cl.duoc.cloudnative.cursos.repository;

import cl.duoc.cloudnative.cursos.model.ContenidoCurso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ContenidoCursoRepository extends JpaRepository<ContenidoCurso, UUID> {

    List<ContenidoCurso> findByCursoId(UUID cursoId);
}
