package ru.job4j.restservice.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import ru.job4j.restservice.model.KafkaMessage;

import java.util.Map;

@Slf4j
public class KafkaMessageDeserializer implements Deserializer<KafkaMessage> {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public KafkaMessage deserialize(String topic, byte[] data) {
        try {
            if (data == null){
                System.out.println("Null received at deserializing");
                return null;
            }
            return objectMapper.readValue(new String(data, "UTF-8"), KafkaMessage.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to KafkaMessage");
        }
    }

    @Override
    public void close() {
    }
}
