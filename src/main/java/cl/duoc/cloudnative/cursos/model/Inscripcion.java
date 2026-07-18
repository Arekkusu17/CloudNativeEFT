package cl.duoc.cloudnative.cursos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "inscripciones")
public class Inscripcion {

    @Id
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @Column(nullable = false, length = 120)
    private String estudiante;

    @Column(nullable = false, length = 160)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private EstadoInscripcion estado;

    @Column(nullable = false)
    private LocalDate fechaInscripcion;

    protected Inscripcion() {
    }

    public Inscripcion(UUID id, Curso curso, String estudiante, String email, EstadoInscripcion estado, LocalDate fechaInscripcion) {
        this.id = id;
        this.curso = curso;
        this.estudiante = estudiante;
        this.email = email;
        this.estado = estado;
        this.fechaInscripcion = fechaInscripcion;
    }

    public UUID getId() {
        return id;
    }

    public Curso getCurso() {
        return curso;
    }

    public String getEstudiante() {
        return estudiante;
    }

    public String getEmail() {
        return email;
    }

    public EstadoInscripcion getEstado() {
        return estado;
    }

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }
}
