package cl.duoc.cloudnative.cursos.config;

import cl.duoc.cloudnative.cursos.model.Curso;
import cl.duoc.cloudnative.cursos.model.EstadoCurso;
import cl.duoc.cloudnative.cursos.model.EstadoExamen;
import cl.duoc.cloudnative.cursos.model.Examen;
import cl.duoc.cloudnative.cursos.repository.CursoRepository;
import cl.duoc.cloudnative.cursos.repository.ExamenRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.UUID;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner inicializarDatos(CursoRepository cursoRepository, ExamenRepository examenRepository) {
        return args -> {
            if (cursoRepository.count() > 0) {
                return;
            }

            Curso curso = new Curso(
                    UUID.fromString("11111111-1111-1111-1111-111111111111"),
                    "Desarrollo Cloud Native",
                    "Curso practico para construir microservicios, APIs, colas y despliegues cloud.",
                    "Equipo Duoc UC",
                    EstadoCurso.PUBLICADO,
                    LocalDate.now()
            );
            cursoRepository.save(curso);

            examenRepository.save(new Examen(
                    UUID.fromString("22222222-2222-2222-2222-222222222222"),
                    curso,
                    "Evaluacion final cloud",
                    100,
                    EstadoExamen.DISPONIBLE
            ));
        };
    }
}
