package edu.java.sender;

import edu.java.models.request.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
@Slf4j
public class KafkaUpdateSender implements UpdateSender {
    private final KafkaTemplate<String, LinkUpdateRequest> updatesProducer;
    private final String topic;

    @Override
    public void sendUpdate(LinkUpdateRequest request) {
        try {
            updatesProducer.send(topic, String.valueOf(request.id()), request);
        } catch (Exception e) {
            log.error("Error has occurred, while send update: {}", request);
        }
    }
}
