package ru.job4j.libraryservice.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.job4j.libraryservice.dto.ListBookDto;
import ru.job4j.libraryservice.ws.BookDto;

import java.util.HashMap;
import java.util.Map;

/**
 * Конфигурация для Kafka
 *
 * @author Alexander Emelyanov
 * @version 1.0
 */
@Configuration
@Slf4j
public class KafkaConfig {

    /**
     * Наименование группы потребителей Kafka
     */
    @Value("${library-project.consumer-group}")
    private String consumerGroups;

    /**
     * Параметры сервера Kafka
     */
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    /**
     * Метод создает бин фабрики потребителей.
     *
     * @return возвращает фабрику потребителей параметризованную BookDto
     */
    @Bean
    public ConsumerFactory<String, BookDto> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroups);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(),
                new JsonDeserializer<>(BookDto.class));
    }

    /**
     * Метод создает бин фабрики производителей.
     *
     * @return возвращает фабрику производителей параметризованную BookDto
     */
    @Bean
    public ProducerFactory<String, BookDto> producerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(props, new StringSerializer(),
                new JsonSerializer<>());
    }

    /**
     * Метод создает бин фабрики контейнеров слушателя Kafka.
     *
     * @return возвращает фабрику контейнеров слушателя Kafka параметризованную BookDto
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, BookDto> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, BookDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setReplyTemplate(replyTemplate());
        return factory;
    }

    /**
     * Метод создает бин для шаблона ответа Kafka.
     *
     * @return возвращает шаблон ответа Kafka параметризованный BookDto
     */
    @Bean
    public KafkaTemplate<String, BookDto> replyTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    /**
     * Метод создает бин фабрику контейнеров слушателя Kafka для работы со списком всех книг.
     *
     * @return возвращает фабрику контейнеров слушателя Kafka параметризованную BookDto
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, BookDto> listKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, BookDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setReplyTemplate(listReplyTemplate());
        return factory;
    }

    /**
     * Метод создает бин фабрики производителей для работы со списком всех книг.
     *
     * @return возвращает фабрику производителей параметризованный ListBookDto
     */
    @Bean
    public ProducerFactory<String, ListBookDto> listProducerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(props, new StringSerializer(),
                new JsonSerializer<>());
    }

    /**
     * Метод создает бин для шаблона ответа Kafka для работы со списком всех книг.
     *
     * @return возвращает шаблон ответа Kafka параметризованный ListBookDto
     */
    @Bean
    public KafkaTemplate<String, ListBookDto> listReplyTemplate() {
        return new KafkaTemplate<>(listProducerFactory());
    }

}
