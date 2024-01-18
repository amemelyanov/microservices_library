package ru.job4j.libraryservice.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import ru.job4j.libraryservice.model.KafkaMessage;

import java.util.Map;

public class KafkaMessageSerializer implements Serializer<KafkaMessage> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, KafkaMessage data) {
        try {
            if (data == null){
                System.out.println("Null received at serializing");
                return null;
            }
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new SerializationException("Error when serializing KafkaMessage to byte[]");
        }
    }

    @Override
    public void close() {
    }
}
