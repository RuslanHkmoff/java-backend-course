package edu.java.sender;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.models.request.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
@Slf4j
public class KafkaUpdateSender implements UpdateSender {
    public static final String KAFKA_TOPIC = "messages.scrapper";
    private final KafkaTemplate<String, String> updatesProducer;

    @Override
    public void sendUpdate(LinkUpdateRequest request) {
        try {
            updatesProducer.send(
                KAFKA_TOPIC,
                String.valueOf(request.id()),
                new ObjectMapper().writeValueAsString(request)
            );
        } catch (Exception e) {
            log.error("Error has occurred, while send update: {}, {}", e.getMessage(), e.getStackTrace());
        }
    }
}
