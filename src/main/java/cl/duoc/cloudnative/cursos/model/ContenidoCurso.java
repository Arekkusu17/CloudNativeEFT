package cl.duoc.cloudnative.cursos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "contenidos_curso")
public class ContenidoCurso {

    @Id
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @Column(nullable = false, length = 120)
    private String titulo;

    @Column(nullable = false, length = 180)
    private String nombreArchivo;

    @Column(nullable = false, length = 120)
    private String contentType;

    @Column(nullable = false, length = 260)
    private String s3Key;

    @Column(nullable = false)
    private LocalDateTime fechaCarga;

    protected ContenidoCurso() {
    }

    public ContenidoCurso(UUID id, Curso curso, String titulo, String nombreArchivo, String contentType, String s3Key, LocalDateTime fechaCarga) {
        this.id = id;
        this.curso = curso;
        this.titulo = titulo;
        this.nombreArchivo = nombreArchivo;
        this.contentType = contentType;
        this.s3Key = s3Key;
        this.fechaCarga = fechaCarga;
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

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public String getContentType() {
        return contentType;
    }

    public String getS3Key() {
        return s3Key;
    }

    public LocalDateTime getFechaCarga() {
        return fechaCarga;
    }
}
