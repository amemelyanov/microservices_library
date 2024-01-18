package ru.job4j.restservice.config;

import lombok.extern.slf4j.Slf4j;
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
import ru.job4j.restservice.model.KafkaMessage;
import ru.job4j.restservice.wsdl.BookInfo;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class KafkaListConfig {

    @Value("${library-project.reply-topics-all}")
    private String REPLY_TOPICS_ALL;

    @Value("${library-project.consumer-group}")
    private String CONSUMER_GROUPS;

    @Value("${spring.kafka.bootstrap-servers}")
    private String BOOTSTRAP_SERVERS;


    @Bean
    public ConsumerFactory<String, KafkaMessage> consumerFactory2() {
        Map<String, Object> props = new HashMap<>();
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "ru.job4j.libraryservice.ws.BookInfo");
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, CONSUMER_GROUPS);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                "ru.job4j.restservice.deserializer.KafkaMessageDeserializer");
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ProducerFactory<String, BookInfo> producerFactory2() {
        Map<String, Object> props = new HashMap<>();
        props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(props, new StringSerializer(),
                new JsonSerializer<>());
    }

    @Bean
    public ReplyingKafkaTemplate<String, BookInfo, KafkaMessage> replyingTemplate2(
            ConcurrentMessageListenerContainer<String, KafkaMessage> repliesContainer2) {
        ReplyingKafkaTemplate<String, BookInfo, KafkaMessage> replyTemplate =
                new ReplyingKafkaTemplate<>(producerFactory2(),
                repliesContainer2);
        replyTemplate.setDefaultReplyTimeout(Duration.ofSeconds(60));
        replyTemplate.setSharedReplyTopic(true);
        return replyTemplate;
    }

    @Bean
    public ConcurrentMessageListenerContainer<String, KafkaMessage> repliesContainer2(
            ConcurrentKafkaListenerContainerFactory<String, KafkaMessage> containerFactory2) {
        ConcurrentMessageListenerContainer<String, KafkaMessage> repliesContainer =
                containerFactory2.createContainer(REPLY_TOPICS_ALL);
        repliesContainer.setAutoStartup(false);
        return repliesContainer;
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, KafkaMessage> kafkaListenerContainerFactory2() {
        ConcurrentKafkaListenerContainerFactory<String, KafkaMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory2());
        return factory;
    }
}
