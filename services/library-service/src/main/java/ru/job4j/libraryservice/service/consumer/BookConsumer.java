package ru.job4j.libraryservice.service.consumer;

import ru.job4j.libraryservice.ws.FindBookByIdRequest;

public interface BookConsumer {

    void findBookByIdListener(FindBookByIdRequest request);

    void findAllBooksListener();
}
