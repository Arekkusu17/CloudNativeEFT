package cl.duoc.cloudnative.cursos.repository;

import cl.duoc.cloudnative.cursos.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CursoRepository extends JpaRepository<Curso, UUID> {

    List<Curso> findByInstructorContainingIgnoreCase(String instructor);
}
