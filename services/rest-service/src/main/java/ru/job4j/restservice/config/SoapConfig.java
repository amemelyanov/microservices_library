package ru.job4j.restservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import ru.job4j.restservice.mapper.BookMapper;
import ru.job4j.restservice.service.BookServiceSoap;

@Configuration
public class SoapConfig {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("ru.job4j.restservice.wsdl");
        return marshaller;
    }

    @Bean
    public BookServiceSoap bookServiceSoap(Jaxb2Marshaller marshaller) {
        BookServiceSoap service = new BookServiceSoap(new BookMapper());
        service.setDefaultUri("http://library-service:8081/ws");
        service.setMarshaller(marshaller);
        service.setUnmarshaller(marshaller);
        return service;
    }
}
