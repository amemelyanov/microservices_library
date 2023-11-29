package ru.job4j.libraryservice.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import ru.job4j.libraryservice.ws.FindBookByIdRequest;

import java.util.Map;

public class FindBookByIdRequestDeserializer implements Deserializer<FindBookByIdRequest> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public FindBookByIdRequest deserialize(String topic, byte[] data) {
        try {
            if (data == null){
                System.out.println("Null received at deserializing");
                return null;
            }
            return objectMapper.readValue(new String(data, "UTF-8"),
                    FindBookByIdRequest.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to FindBookByIdRequest");
        }
    }

    @Override
    public void close() {
    }
}