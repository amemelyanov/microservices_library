package ru.job4j.restservice.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import ru.job4j.restservice.wsdl.GetBookRequest;
import ru.job4j.restservice.wsdl.GetBookResponse;


public class BookClient extends WebServiceGatewaySupport {

    private static final Logger LOG = LoggerFactory.getLogger(BookClient.class);

    public GetBookResponse getBook(int id) {

        GetBookRequest request = new GetBookRequest();
        request.setId(id);

        LOG.info("Requesting location for " + id);

        GetBookResponse response = (GetBookResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:8081/ws/books", request,
                        new SoapActionCallback(
                                "http://libraryservice.job4j.ru/model/GetBookRequest"));

        return response;
    }

}
