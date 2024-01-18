package ru.job4j.libraryservice.mapper;

import org.springframework.messaging.Message;
import ru.job4j.libraryservice.model.KafkaObject;
import ru.job4j.libraryservice.ws.BookInfo;

public class MessageBuilder {

    public static Message<KafkaObject<BookInfo>> createResponse(KafkaObject<BookInfo> request) {
//        String reversedString = new StringBuilder(String.valueOf(request.getName())).reverse().toString();
//        request.setName(reversedString);
        return org.springframework.messaging.support.MessageBuilder.withPayload(request).build();
    }
}
