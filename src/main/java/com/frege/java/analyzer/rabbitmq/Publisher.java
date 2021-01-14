package com.frege.java.analyzer.rabbitmq;

import com.google.gson.Gson;
import com.frege.java.analyzer.rabbitmq.model.SentMessage;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Publisher {
    private static final String GARBAGE_COLLECTOR_QUEUE = "gc";
    private static final Integer LANGUAGE_ID = 5;

    private final RabbitTemplate rabbitTemplate;
    private final Gson gson;

    @Autowired
    public Publisher(RabbitTemplate rabbitTemplate, Gson gson) {
        this.rabbitTemplate = rabbitTemplate;
        this.gson = gson;
    }

    public void publishMessage(String repoId) {
        String json = gson.toJson(new SentMessage(repoId, LANGUAGE_ID));
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/json");
        Message message = new Message(json.getBytes(), messageProperties);
        rabbitTemplate.invoke(operation -> {
                    operation.convertAndSend(GARBAGE_COLLECTOR_QUEUE, message);
                    return operation.waitForConfirms(10000);
                }
        );
    }
}
