CREATE TABLE IF NOT EXISTS cursos (
    id UUID PRIMARY KEY,
    titulo VARCHAR(120) NOT NULL,
    descripcion VARCHAR(500) NOT NULL,
    instructor VARCHAR(120) NOT NULL,
    estado VARCHAR(30) NOT NULL,
    fecha_creacion DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS contenidos_curso (
    id UUID PRIMARY KEY,
    curso_id UUID NOT NULL,
    titulo VARCHAR(120) NOT NULL,
    nombre_archivo VARCHAR(180) NOT NULL,
    content_type VARCHAR(120) NOT NULL,
    s3_key VARCHAR(260) NOT NULL,
    fecha_carga TIMESTAMP NOT NULL,
    CONSTRAINT fk_contenidos_curso FOREIGN KEY (curso_id) REFERENCES cursos(id)
);

CREATE TABLE IF NOT EXISTS inscripciones (
    id UUID PRIMARY KEY,
    curso_id UUID NOT NULL,
    estudiante VARCHAR(120) NOT NULL,
    email VARCHAR(160) NOT NULL,
    estado VARCHAR(30) NOT NULL,
    fecha_inscripcion DATE NOT NULL,
    CONSTRAINT fk_inscripciones_curso FOREIGN KEY (curso_id) REFERENCES cursos(id)
);

CREATE TABLE IF NOT EXISTS examenes (
    id UUID PRIMARY KEY,
    curso_id UUID NOT NULL,
    titulo VARCHAR(120) NOT NULL,
    puntaje_maximo INTEGER NOT NULL,
    estado VARCHAR(30) NOT NULL,
    CONSTRAINT fk_examenes_curso FOREIGN KEY (curso_id) REFERENCES cursos(id)
);

CREATE TABLE IF NOT EXISTS calificaciones (
    id UUID PRIMARY KEY,
    examen_id UUID NOT NULL,
    estudiante VARCHAR(120) NOT NULL,
    puntaje INTEGER NOT NULL,
    estado VARCHAR(30) NOT NULL,
    fecha_calificacion TIMESTAMP NOT NULL,
    CONSTRAINT fk_calificaciones_examen FOREIGN KEY (examen_id) REFERENCES examenes(id)
);

CREATE TABLE IF NOT EXISTS evaluaciones_procesadas (
    id UUID PRIMARY KEY,
    evaluacion_id UUID NOT NULL,
    examen_id UUID NOT NULL,
    estudiante VARCHAR(120) NOT NULL,
    puntaje INTEGER NOT NULL,
    fecha_procesamiento TIMESTAMP NOT NULL
);
