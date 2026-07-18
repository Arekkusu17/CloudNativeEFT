package cl.duoc.cloudnative.cursos.service;

import cl.duoc.cloudnative.cursos.dto.EvaluacionErrorMessage;
import cl.duoc.cloudnative.cursos.dto.EvaluacionMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EvaluacionProducer {

    private final RabbitTemplate rabbitTemplate;
    private final String exchangeEvaluaciones;
    private final String exchangeErrores;
    private final String routingEvaluacionRendida;
    private final String routingEvaluacionError;

    public EvaluacionProducer(
            RabbitTemplate rabbitTemplate,
            @Value("${rabbitmq.exchange.evaluaciones}") String exchangeEvaluaciones,
            @Value("${rabbitmq.exchange.errores}") String exchangeErrores,
            @Value("${rabbitmq.routing.evaluacion.rendida}") String routingEvaluacionRendida,
            @Value("${rabbitmq.routing.evaluacion.error}") String routingEvaluacionError
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchangeEvaluaciones = exchangeEvaluaciones;
        this.exchangeErrores = exchangeErrores;
        this.routingEvaluacionRendida = routingEvaluacionRendida;
        this.routingEvaluacionError = routingEvaluacionError;
    }

    public void publicarEvaluacion(EvaluacionMessage message) {
        rabbitTemplate.convertAndSend(exchangeEvaluaciones, routingEvaluacionRendida, message);
    }

    public void publicarError(EvaluacionErrorMessage message) {
        rabbitTemplate.convertAndSend(exchangeErrores, routingEvaluacionError, message);
    }
}
