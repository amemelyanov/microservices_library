package ru.job4j.restservice.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import ru.job4j.restservice.serializer.FindAllBookRequestSerializer;
import ru.job4j.restservice.serializer.FindBookByIdRequestSerializer;
import ru.job4j.restservice.wsdl.FindAllBooksRequest;
import ru.job4j.restservice.wsdl.FindBookByIdRequest;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class FindAllBooksRequestConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServer;

    @Bean
    public Map<String, Object> findAllBooksRequestProducerConfig() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                FindAllBookRequestSerializer.class);
        return properties;
    }

    @Bean
    public ProducerFactory<Long, FindAllBooksRequest> findAllBooksRequestProducerFactory() {
        return new DefaultKafkaProducerFactory<>(findAllBooksRequestProducerConfig());
    }

    @Bean("findAllBooksRequestKafkaTemplate")
    public KafkaTemplate<Long, FindAllBooksRequest> findAllBooksRequestKafkaTemplate() {
        return new KafkaTemplate<>(findAllBooksRequestProducerFactory());
    }
}