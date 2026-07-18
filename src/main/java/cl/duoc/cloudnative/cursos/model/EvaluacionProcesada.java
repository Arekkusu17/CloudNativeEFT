package cl.duoc.cloudnative.cursos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "evaluaciones_procesadas")
public class EvaluacionProcesada {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID evaluacionId;

    @Column(nullable = false)
    private UUID examenId;

    @Column(nullable = false, length = 120)
    private String estudiante;

    @Column(nullable = false)
    private Integer puntaje;

    @Column(nullable = false)
    private LocalDateTime fechaProcesamiento;

    protected EvaluacionProcesada() {
    }

    public EvaluacionProcesada(UUID id, UUID evaluacionId, UUID examenId, String estudiante, Integer puntaje, LocalDateTime fechaProcesamiento) {
        this.id = id;
        this.evaluacionId = evaluacionId;
        this.examenId = examenId;
        this.estudiante = estudiante;
        this.puntaje = puntaje;
        this.fechaProcesamiento = fechaProcesamiento;
    }
}
