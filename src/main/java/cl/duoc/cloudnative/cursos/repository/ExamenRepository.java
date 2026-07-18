package cl.duoc.cloudnative.cursos.repository;

import cl.duoc.cloudnative.cursos.model.Examen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ExamenRepository extends JpaRepository<Examen, UUID> {

    List<Examen> findByCursoId(UUID cursoId);
}
