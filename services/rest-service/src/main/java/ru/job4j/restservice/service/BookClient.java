package ru.job4j.restservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import ru.job4j.restservice.wsdl.FindAllBooksRequest;
import ru.job4j.restservice.wsdl.FindAllBooksResponse;
import ru.job4j.restservice.wsdl.FindBookByIdRequest;
import ru.job4j.restservice.wsdl.FindBookByIdResponse;

@Slf4j
public class BookClient extends WebServiceGatewaySupport {

    public FindBookByIdResponse findBookById(long bookId) {
        log.info("Вызов метода findBookById() класса BookClient с параметром bookId = {}", bookId);
        FindBookByIdRequest request = new FindBookByIdRequest();
        request.setBookId(bookId);
        FindBookByIdResponse response = (FindBookByIdResponse) getWebServiceTemplate().marshalSendAndReceive(request,
                new SoapActionCallback("http://library-service:8081/ws/findBookByIdRequest"));
        log.info("Получен ответ от SOAP сервиса в методе findBookById() класса BookClient с объектом {}", response);
        return response;
    }

    public FindAllBooksResponse findAllBooks() {
        log.info("Вызов метода findAllBooks() класса BookClient");
        FindAllBooksRequest request = new FindAllBooksRequest();
        FindAllBooksResponse response = (FindAllBooksResponse) getWebServiceTemplate().marshalSendAndReceive(request,
                new SoapActionCallback("http://library-service:8081/ws/findAllBooksRequest"));
        log.info("Получен ответ от SOAP сервиса в методе findAllBooks() класса BookClient с объектом {}", response);
        return response;
    }

}
