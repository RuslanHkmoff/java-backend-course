package edu.java.bot.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.bot.listener.validator.RequestValidator;
import edu.java.bot.service.AlertService;
import edu.java.models.request.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class LinkUpdateListener {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String DLQ_TOPIC = "messages.scrapper_dlq";

    private final KafkaTemplate<String, String> deadLetterQueue;
    private final AlertService alertService;

    @SneakyThrows
    @KafkaListener(topics = "messages.scrapper", groupId = "messages-group")
    public void handleUpdate(@Payload String message) {
        log.info("Received message from kafka: {}", message);
        LinkUpdateRequest updateRequest = OBJECT_MAPPER.readValue(message, LinkUpdateRequest.class);
        try {
            RequestValidator.isValidRequestOrElseThrow(updateRequest);
            alertService.sendAlert(updateRequest);
        } catch (Exception e) {
            log.info("Invalid update, send to dlq");
            deadLetterQueue.send(DLQ_TOPIC, message);
        }
    }
}
