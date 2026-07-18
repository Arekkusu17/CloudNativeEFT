package cl.duoc.cloudnative.cursos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    private UUID id;

    @Column(nullable = false, length = 120)
    private String titulo;

    @Column(nullable = false, length = 500)
    private String descripcion;

    @Column(nullable = false, length = 120)
    private String instructor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private EstadoCurso estado;

    @Column(nullable = false)
    private LocalDate fechaCreacion;

    protected Curso() {
    }

    public Curso(UUID id, String titulo, String descripcion, String instructor, EstadoCurso estado, LocalDate fechaCreacion) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.instructor = instructor;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
    }

    public UUID getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getInstructor() {
        return instructor;
    }

    public EstadoCurso getEstado() {
        return estado;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void actualizar(String titulo, String descripcion, String instructor, EstadoCurso estado) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.instructor = instructor;
        this.estado = estado;
    }
}
