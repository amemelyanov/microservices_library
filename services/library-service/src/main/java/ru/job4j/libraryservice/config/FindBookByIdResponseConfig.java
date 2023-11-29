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
import ru.job4j.libraryservice.serializer.FindBookByIdResponseSerializer;
import ru.job4j.libraryservice.ws.FindBookByIdResponse;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class FindBookByIdResponseConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServer;

//    @Value("${spring.kafka.consumer.group-id}")
//    private String kafkaGroupId;

    @Bean
    public Map<String, Object> findByIdBookResponseConsumerConfig() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
//        properties.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaGroupId);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                FindBookByIdResponseSerializer.class);
        return properties;
    }

    @Bean
    public ConsumerFactory<Long, FindBookByIdResponse> findByIdBookResponseConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(findByIdBookResponseConsumerConfig());
    }

    @Bean
    public KafkaListenerContainerFactory<?> findByIdBookresponseKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Long, FindBookByIdResponse> factory =
                new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(findByIdBookResponseConsumerFactory());
        return factory;
    }
}