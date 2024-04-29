package ru.job4j.restservice.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.job4j.restservice.wsdl.BookDto;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Конфигурация для Kafka работающего с сущностью dto книги
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.restservice.wsdl.BookDto
 */
@Configuration
public class KafkaEntityConfig {

    /**
     * Идентификатор топика ответа Kafka
     */
    @Value("${library-project.reply-topics-by-id}")
    private String replyTopicsById;

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
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props,
                new StringDeserializer(),
                new JsonDeserializer<>(BookDto.class));
    }

    /**
     * Метод создает бин фабрики производителей.
     *
     * @return возвращает фабрику производителей параметризованную Long
     */
    @Bean
    public ProducerFactory<String, Long> producerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        return new DefaultKafkaProducerFactory<>(props, new StringSerializer(),
                new LongSerializer());
    }

    /**
     * Метод создает бин для шаблона ответа Kafka.
     *
     * @param repliesContainer контейнер ответа
     * @return возвращает шаблон ответа Kafka параметризованный Long и BookDto
     */
    @Bean
    public ReplyingKafkaTemplate<String, Long, BookDto> replyingTemplate(
            ConcurrentMessageListenerContainer<String, BookDto> repliesContainer) {
        ReplyingKafkaTemplate<String, Long, BookDto> replyTemplate =
                new ReplyingKafkaTemplate<>(producerFactory(),
                        repliesContainer);
        replyTemplate.setDefaultReplyTimeout(Duration.ofSeconds(20));
        replyTemplate.setSharedReplyTopic(true);
        return replyTemplate;
    }

    /**
     * Метод создает бин контейнера для шаблона ответа Kafka.
     *
     * @param containerFactory фабрика контейнеров
     * @return возвращает контейнер ответа Kafka параметризованный BookDto
     */
    @Bean
    public ConcurrentMessageListenerContainer<String, BookDto> repliesContainer(
            ConcurrentKafkaListenerContainerFactory<String, BookDto> containerFactory) {
        ConcurrentMessageListenerContainer<String, BookDto> repliesContainer =
                containerFactory.createContainer(replyTopicsById);
        repliesContainer.setAutoStartup(false);
        return repliesContainer;
    }

    /**
     * Метод создает бин фабрики контейнера слушателя для Kafka.
     *
     * @return фабрику контейнеров слушателя Kafka параметризованную BookDto
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, BookDto> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, BookDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
