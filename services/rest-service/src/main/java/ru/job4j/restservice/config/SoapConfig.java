package ru.job4j.restservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import ru.job4j.restservice.mapper.BookMapper;
import ru.job4j.restservice.service.BookServiceSoap;

/**
 * Конфигурация для SOAP
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.restservice.dto.ListBookDto
 */
@Configuration
public class SoapConfig {

    /**
     * Метод создает бин Jaxb2 преобразователя с установкой контекстного пути.
     *
     * @return бин Jaxb2 преобразователя.
     */
    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("ru.job4j.restservice.wsdl");
        return marshaller;
    }

    /**
     * Метод создает бин SOAP сервиса.
     *
     * @param marshaller объект Jaxb2 преобразователя
     * @return бин SOAP сервиса.
     */
    @Bean
    public BookServiceSoap bookServiceSoap(Jaxb2Marshaller marshaller) {
        BookServiceSoap service = new BookServiceSoap(new BookMapper());
        service.setDefaultUri("http://library-service:8081/ws");
        service.setMarshaller(marshaller);
        service.setUnmarshaller(marshaller);
        return service;
    }
}
