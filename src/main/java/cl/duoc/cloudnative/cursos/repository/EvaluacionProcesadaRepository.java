package cl.duoc.cloudnative.cursos.repository;

import cl.duoc.cloudnative.cursos.model.EvaluacionProcesada;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EvaluacionProcesadaRepository extends JpaRepository<EvaluacionProcesada, UUID> {
}
