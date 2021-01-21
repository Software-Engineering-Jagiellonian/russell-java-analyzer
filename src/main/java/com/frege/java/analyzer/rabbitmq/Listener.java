package com.frege.java.analyzer.rabbitmq;

import com.frege.java.analyzer.analyzers.model.Statistic;
import com.frege.java.analyzer.database.model.RepositoryJavaStatistics;
import com.google.gson.Gson;
import com.frege.java.analyzer.analyzers.AnalyzerService;
import com.frege.java.analyzer.database.DatabaseService;
import com.frege.java.analyzer.rabbitmq.model.ReceivedMessage;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class Listener {
    private final Publisher publisher;
    private final DatabaseService databaseService;
    private final Gson gson;
    private final AnalyzerService analyzerService;

    @Autowired
    public Listener(Publisher publisher, DatabaseService databaseService, Gson gson, AnalyzerService analyzerService) {
        this.publisher = publisher;
        this.databaseService = databaseService;
        this.gson = gson;
        this.analyzerService = analyzerService;
    }

    @RabbitListener(queues = "analyze-java", ackMode = "MANUAL")
    public void listen(Message event, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        ReceivedMessage receivedMessage = gson.fromJson(new String(event.getBody(), StandardCharsets.UTF_8), ReceivedMessage.class);

        Long analyzeRepoId = databaseService.getRepositoryLanguageIdByRepositoryId(receivedMessage.getRepositoryId());
        String path = databaseService.getPath(analyzeRepoId);

        List<RepositoryJavaStatistics> statistics = analyzerService.analyze(path)
                .map(it -> convert(it, receivedMessage.getRepositoryId()))
                .collect(Collectors.toUnmodifiableList());
        databaseService.saveStatistics(statistics);

        publisher.publishMessage(receivedMessage.getRepositoryId());
        channel.basicAck(tag, false);

        databaseService.updateAnalyzed(analyzeRepoId);
    }

    private RepositoryJavaStatistics convert(Statistic statistic, String repositoryId) {
        return new RepositoryJavaStatistics(
                generateUUID(),
                repositoryId,
                statistic.getFilename(),
                statistic.getLine(),
                statistic.getType(),
                statistic.getReason()
        );
    }

    private String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
