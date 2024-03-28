package ru.job4j.libraryservice.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

/**
 * Конфигурация для SOAP сервиса
 *
 * @author Alexander Emelyanov
 * @version 1.0
 */
@EnableWs
@Configuration
public class WebServiceConfig {

    /**
     * Метод создает бин регистрации сервлетов.
     *
     * @param applicationContext контекст приложения
     * @return возвращает бин регистрации сервлетов параметризованный MessageDispatcherServlet
     */
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(
            ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    /**
     * Метод создает бин реализации Wsdl11Definition.
     *
     * @param booksSchema xsd схема
     * @return возвращает бин реализации Wsdl11Definition
     */
    @Bean(name = "books")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema booksSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("BooksPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://libraryservice.job4j.ru/ws");
        wsdl11Definition.setSchema(booksSchema);
        return wsdl11Definition;
    }

    /**
     * Метод создает бин xsd схемы на основании SOAP xsd схемы приложения.
     *
     * @return возвращает бин xsd схемы
     */
    @Bean
    public XsdSchema booksSchema() {
        return new SimpleXsdSchema(new ClassPathResource("books.xsd"));
    }
}
