package ru.job4j.restservice.service;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import ru.job4j.restservice.wsdl.FindAllBooksRequest;
import ru.job4j.restservice.wsdl.FindAllBooksResponse;
import ru.job4j.restservice.wsdl.FindBookByIdRequest;
import ru.job4j.restservice.wsdl.FindBookByIdResponse;

public class BookClient extends WebServiceGatewaySupport {

    public FindBookByIdResponse findBookById(long bookId) {
        FindBookByIdRequest request = new FindBookByIdRequest();
        request.setBookId(bookId);
        FindBookByIdResponse response = (FindBookByIdResponse) getWebServiceTemplate().marshalSendAndReceive(request,
                new SoapActionCallback("http://library-service:8081/ws/findBookByIdRequest"));
        return response;
    }

    public FindAllBooksResponse findAllBooks() {
        FindAllBooksRequest request = new FindAllBooksRequest();
        FindAllBooksResponse response = (FindAllBooksResponse) getWebServiceTemplate().marshalSendAndReceive(request,
                new SoapActionCallback("http://library-service:8081/ws/findAllBooksRequest"));
        return response;
    }

}
