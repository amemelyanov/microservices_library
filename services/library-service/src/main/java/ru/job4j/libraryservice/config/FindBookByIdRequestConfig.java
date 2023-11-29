package ru.job4j.libraryservice.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import ru.job4j.libraryservice.deserializer.FindBookByIdRequestDeserializer;
import ru.job4j.libraryservice.ws.FindBookByIdRequest;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class FindBookByIdRequestConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServer;

    @Value("${spring.kafka.consumer.group-id}")
    private String kafkaGroupId;

    @Bean
    public Map<String, Object> findByIdBookRequestConsumerConfig() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaGroupId);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                FindBookByIdRequestDeserializer.class);
        return properties;
    }

    @Bean
    public ConsumerFactory<Long, FindBookByIdRequest> findByIdBookRequestConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(findByIdBookRequestConsumerConfig());
    }

    @Bean
    public KafkaListenerContainerFactory<?> findByIdBookRequestKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Long, FindBookByIdRequest> factory =
                new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(findByIdBookRequestConsumerFactory());
        return factory;
    }
}