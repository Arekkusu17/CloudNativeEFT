package cl.duoc.cloudnative.cursos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "calificaciones")
public class Calificacion {

    @Id
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "examen_id")
    private Examen examen;

    @Column(nullable = false, length = 120)
    private String estudiante;

    @Column(nullable = false)
    private Integer puntaje;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private EstadoCalificacion estado;

    @Column(nullable = false)
    private LocalDateTime fechaCalificacion;

    protected Calificacion() {
    }

    public Calificacion(UUID id, Examen examen, String estudiante, Integer puntaje, EstadoCalificacion estado, LocalDateTime fechaCalificacion) {
        this.id = id;
        this.examen = examen;
        this.estudiante = estudiante;
        this.puntaje = puntaje;
        this.estado = estado;
        this.fechaCalificacion = fechaCalificacion;
    }

    public UUID getId() {
        return id;
    }

    public Examen getExamen() {
        return examen;
    }

    public String getEstudiante() {
        return estudiante;
    }

    public Integer getPuntaje() {
        return puntaje;
    }

    public EstadoCalificacion getEstado() {
        return estado;
    }

    public LocalDateTime getFechaCalificacion() {
        return fechaCalificacion;
    }

    public void marcarProcesada() {
        this.estado = EstadoCalificacion.PROCESADA;
    }
}
