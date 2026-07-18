package cl.duoc.cloudnative.cursos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "examenes")
public class Examen {

    @Id
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @Column(nullable = false, length = 120)
    private String titulo;

    @Column(nullable = false)
    private Integer puntajeMaximo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private EstadoExamen estado;

    protected Examen() {
    }

    public Examen(UUID id, Curso curso, String titulo, Integer puntajeMaximo, EstadoExamen estado) {
        this.id = id;
        this.curso = curso;
        this.titulo = titulo;
        this.puntajeMaximo = puntajeMaximo;
        this.estado = estado;
    }

    public UUID getId() {
        return id;
    }

    public Curso getCurso() {
        return curso;
    }

    public String getTitulo() {
        return titulo;
    }

    public Integer getPuntajeMaximo() {
        return puntajeMaximo;
    }

    public EstadoExamen getEstado() {
        return estado;
    }
}
