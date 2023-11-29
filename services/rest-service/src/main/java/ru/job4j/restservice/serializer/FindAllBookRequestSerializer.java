package ru.job4j.restservice.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import ru.job4j.restservice.wsdl.FindAllBooksRequest;

import java.util.Map;

public class FindAllBookRequestSerializer implements Serializer<FindAllBooksRequest> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, FindAllBooksRequest data) {
        try {
            if (data == null){
                System.out.println("Null received at serializing");
                return null;
            }
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new SerializationException("Error when serializing FindAllBooksRequest to byte[]");
        }
    }

    @Override
    public void close() {
    }
}