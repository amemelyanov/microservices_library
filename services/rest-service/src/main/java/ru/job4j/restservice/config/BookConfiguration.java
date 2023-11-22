package ru.job4j.restservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import ru.job4j.restservice.service.BookClient;

@Configuration
public class BookConfiguration {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("ru.job4j.restservice.wsdl");
        return marshaller;
    }

    @Bean
    public BookClient bookClient(Jaxb2Marshaller marshaller) {
        BookClient client = new BookClient();
        client.setDefaultUri("http://library-service:8081/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
