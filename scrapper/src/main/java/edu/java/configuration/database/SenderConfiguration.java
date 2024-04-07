package edu.java.configuration.database;

import edu.java.configuration.ApplicationConfig;
import edu.java.models.request.LinkUpdateRequest;
import edu.java.sender.HttpUpdateSender;
import edu.java.sender.KafkaUpdateSender;
import edu.java.sender.UpdateSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

@Configuration
@Slf4j
public class SenderConfiguration {
    @ConditionalOnProperty(value = "app.sender-type", havingValue = "http")
    @Bean
    public UpdateSender httpUpdateSender(WebClient botClient, Retry retryStrategy) {
        log.info("Using http");
        return new HttpUpdateSender(botClient, retryStrategy);
    }

    @ConditionalOnProperty(value = "app.sender-type", havingValue = "kafka")
    @Bean
    public UpdateSender kafkaUpdateSender(
        ApplicationConfig applicationConfig,
        KafkaTemplate<String, LinkUpdateRequest> updateProducer
    ) {
        log.info("Using kafka");
        return new KafkaUpdateSender(updateProducer, applicationConfig.topic().name());
    }
}
