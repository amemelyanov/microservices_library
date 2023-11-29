package ru.job4j.libraryservice.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import ru.job4j.libraryservice.serializer.FindAllBookResponseSerializer;
import ru.job4j.libraryservice.ws.FindAllBooksRequest;
import ru.job4j.libraryservice.ws.FindAllBooksResponse;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class FindAllBooksResponseConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServer;

//    @Value("${spring.kafka.consumer.group-id}")
//    private String kafkaGroupId;

    @Bean
    public Map<String, Object> findAllBooksResponseConsumerConfig() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
//        properties.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaGroupId);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                FindAllBookResponseSerializer.class);
        return properties;
    }

    @Bean
    public ConsumerFactory<Long, FindAllBooksResponse> findAllBooksResponseConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(findAllBooksResponseConsumerConfig());
    }

    @Bean
    public KafkaListenerContainerFactory<?> findAllBooksResponseKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Long, FindAllBooksResponse> factory =
                new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(findAllBooksResponseConsumerFactory());
        return factory;
    }
}