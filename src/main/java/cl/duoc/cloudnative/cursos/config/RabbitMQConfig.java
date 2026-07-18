package cl.duoc.cloudnative.cursos.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQConfig.class);

    @Value("${rabbitmq.exchange.evaluaciones}")
    private String exchangeEvaluaciones;

    @Value("${rabbitmq.exchange.errores}")
    private String exchangeErrores;

    @Value("${rabbitmq.queue.evaluaciones}")
    private String queueEvaluaciones;

    @Value("${rabbitmq.queue.evaluaciones.errores}")
    private String queueEvaluacionesErrores;

    @Value("${rabbitmq.routing.evaluacion.rendida}")
    private String routingEvaluacionRendida;

    @Value("${rabbitmq.routing.evaluacion.error}")
    private String routingEvaluacionError;

    @Bean
    DirectExchange exchangeEvaluaciones() {
        return new DirectExchange(exchangeEvaluaciones, true, false);
    }

    @Bean
    DirectExchange exchangeErrores() {
        return new DirectExchange(exchangeErrores, true, false);
    }

    @Bean
    Queue queueEvaluaciones() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", exchangeErrores);
        args.put("x-dead-letter-routing-key", routingEvaluacionError);
        return new Queue(queueEvaluaciones, true, false, false, args);
    }

    @Bean
    Queue queueEvaluacionesErrores() {
        return new Queue(queueEvaluacionesErrores, true);
    }

    @Bean
    Binding bindingEvaluaciones() {
        return BindingBuilder.bind(queueEvaluaciones()).to(exchangeEvaluaciones()).with(routingEvaluacionRendida);
    }

    @Bean
    Binding bindingEvaluacionesErrores() {
        return BindingBuilder.bind(queueEvaluacionesErrores()).to(exchangeErrores()).with(routingEvaluacionError);
    }

    @Bean
    MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void declararComponentesRabbitMQ(ApplicationReadyEvent event) {
        try {
            RabbitAdmin rabbitAdmin = event.getApplicationContext().getBean(RabbitAdmin.class);
            rabbitAdmin.declareExchange(exchangeEvaluaciones());
            rabbitAdmin.declareExchange(exchangeErrores());
            rabbitAdmin.declareQueue(queueEvaluaciones());
            rabbitAdmin.declareQueue(queueEvaluacionesErrores());
            rabbitAdmin.declareBinding(bindingEvaluaciones());
            rabbitAdmin.declareBinding(bindingEvaluacionesErrores());
            log.info("Componentes RabbitMQ declarados: {}, {}", queueEvaluaciones, queueEvaluacionesErrores);
        } catch (Exception exception) {
            log.warn("No se pudieron declarar componentes RabbitMQ: {}", exception.getMessage());
        }
    }
}
