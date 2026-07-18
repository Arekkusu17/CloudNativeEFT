package cl.duoc.cloudnative.cursos.service;

import cl.duoc.cloudnative.cursos.dto.EvaluacionErrorMessage;
import cl.duoc.cloudnative.cursos.dto.EvaluacionMessage;
import cl.duoc.cloudnative.cursos.dto.ProcesarColaResponse;
import cl.duoc.cloudnative.cursos.model.Calificacion;
import cl.duoc.cloudnative.cursos.model.EvaluacionProcesada;
import cl.duoc.cloudnative.cursos.repository.CalificacionRepository;
import cl.duoc.cloudnative.cursos.repository.EvaluacionProcesadaRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EvaluacionQueueService {

    private final RabbitTemplate rabbitTemplate;
    private final CalificacionRepository calificacionRepository;
    private final EvaluacionProcesadaRepository evaluacionProcesadaRepository;
    private final EvaluacionProducer producer;
    private final String queueEvaluaciones;

    public EvaluacionQueueService(
            RabbitTemplate rabbitTemplate,
            CalificacionRepository calificacionRepository,
            EvaluacionProcesadaRepository evaluacionProcesadaRepository,
            EvaluacionProducer producer,
            @Value("${rabbitmq.queue.evaluaciones}") String queueEvaluaciones
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.calificacionRepository = calificacionRepository;
        this.evaluacionProcesadaRepository = evaluacionProcesadaRepository;
        this.producer = producer;
        this.queueEvaluaciones = queueEvaluaciones;
    }

    @Transactional
    public ProcesarColaResponse procesarPendientes(int limite) {
        int procesados = 0;
        int errores = 0;

        for (int i = 0; i < limite; i++) {
            Object payload = rabbitTemplate.receiveAndConvert(queueEvaluaciones);
            if (payload == null) {
                break;
            }

            try {
                EvaluacionMessage message = (EvaluacionMessage) payload;
                procesar(message);
                procesados++;
            } catch (Exception exception) {
                errores++;
            }
        }

        return new ProcesarColaResponse(procesados, errores, "Procesamiento de cola finalizado");
    }

    private void procesar(EvaluacionMessage message) {
        Calificacion calificacion = calificacionRepository.findById(message.calificacionId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Calificacion no encontrada: " + message.calificacionId()));

        evaluacionProcesadaRepository.save(new EvaluacionProcesada(
                UUID.randomUUID(),
                message.calificacionId(),
                message.examenId(),
                message.estudiante(),
                message.puntaje(),
                LocalDateTime.now()
        ));

        calificacion.marcarProcesada();
        calificacionRepository.save(calificacion);
    }

    public void enviarError(EvaluacionMessage message, String motivo) {
        producer.publicarError(new EvaluacionErrorMessage(
                message.calificacionId(),
                message.examenId(),
                message.estudiante(),
                motivo,
                LocalDateTime.now()
        ));
    }
}
