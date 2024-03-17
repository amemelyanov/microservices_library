package ru.job4j.restservice.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
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
import ru.job4j.restservice.wsdl.BookInfo;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaEntityConfig {

    @Value("${library-project.reply-topics-by-id}")
    private String replyTopicsById;

    @Value("${library-project.consumer-group}")
    private String consumerGroups;

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;


    @Bean
    public ConsumerFactory<String, BookInfo> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroups);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaConsumerFactory<>(props,
                new StringDeserializer(),
                new JsonDeserializer<>(BookInfo.class));
    }

    @Bean
    public ProducerFactory<String, BookInfo> producerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(props, new StringSerializer(),
                new JsonSerializer<>());
    }

    @Bean
    public ReplyingKafkaTemplate<String, BookInfo, BookInfo> replyingTemplate(
            ConcurrentMessageListenerContainer<String, BookInfo> repliesContainer) {
        ReplyingKafkaTemplate<String, BookInfo, BookInfo> replyTemplate =
                new ReplyingKafkaTemplate<>(producerFactory(),
                repliesContainer);
        replyTemplate.setDefaultReplyTimeout(Duration.ofSeconds(60));
        replyTemplate.setSharedReplyTopic(true);
        return replyTemplate;
    }

    @Bean
    public ConcurrentMessageListenerContainer<String, BookInfo> repliesContainer(
            ConcurrentKafkaListenerContainerFactory<String, BookInfo> containerFactory) {
        ConcurrentMessageListenerContainer<String, BookInfo> repliesContainer =
                containerFactory.createContainer(replyTopicsById);
        repliesContainer.setAutoStartup(false);
        return repliesContainer;
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, BookInfo> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, BookInfo> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
