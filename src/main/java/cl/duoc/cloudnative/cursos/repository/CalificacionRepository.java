package cl.duoc.cloudnative.cursos.repository;

import cl.duoc.cloudnative.cursos.model.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CalificacionRepository extends JpaRepository<Calificacion, UUID> {

    List<Calificacion> findByExamenId(UUID examenId);
}
