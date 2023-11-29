package ru.job4j.restservice.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import ru.job4j.restservice.serializer.FindBookByIdRequestSerializer;
import ru.job4j.restservice.wsdl.FindBookByIdRequest;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class FindBookByIdRequestConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServer;

    @Bean
    public Map<String, Object> findBookByIdRequestProducerConfig() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                FindBookByIdRequestSerializer.class);
        return properties;
    }

    @Bean
    public ProducerFactory<Long, FindBookByIdRequest> findBookByIdRequestProducerFactory() {
        return new DefaultKafkaProducerFactory<>(findBookByIdRequestProducerConfig());
    }

    @Bean("findBookByIdRequestKafkaTemplate")
    public KafkaTemplate<Long, FindBookByIdRequest> findBookByIdRequestKafkaTemplate() {
        return new KafkaTemplate<>(findBookByIdRequestProducerFactory());
    }
}