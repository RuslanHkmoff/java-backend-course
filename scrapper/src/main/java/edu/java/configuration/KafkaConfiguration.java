package edu.java.configuration;

import edu.java.models.request.LinkUpdateRequest;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.validation.annotation.Validated;

@Validated
@ConditionalOnProperty(prefix = "app", name = "sender-type", havingValue = "kafka")
public class KafkaConfiguration {
    private final ApplicationConfig.Kafka kafka;
    private final ApplicationConfig.Topic topic;

    public KafkaConfiguration(ApplicationConfig applicationConfig) {
        kafka = applicationConfig.kafka();
        topic = applicationConfig.topic();
    }

    @Bean
    public KafkaAdmin admin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.bootstrapServers());
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topic1() {
        return TopicBuilder
            .name(topic.name())
            .partitions(topic.partitions())
            .replicas(topic.replicas())
            .compact()
            .build();
    }

    @Bean
    public ProducerFactory<String, LinkUpdateRequest> producerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.bootstrapServers());
        props.put(ProducerConfig.CLIENT_ID_CONFIG, kafka.clientId());
        props.put(ProducerConfig.ACKS_CONFIG, kafka.askMode());
        props.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, (int) kafka.deliveryTimeout().toMillis());
        props.put(ProducerConfig.LINGER_MS_CONFIG, kafka.lingerMs());
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, kafka.batchSize());
        props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, kafka.maxInFlightPerConnection());
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, kafka.enableIdempotence());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, LinkUpdateRequest> updateProducer(
        ProducerFactory<String, LinkUpdateRequest> producerFactory
    ) {
        return new KafkaTemplate<>(producerFactory);
    }

}
