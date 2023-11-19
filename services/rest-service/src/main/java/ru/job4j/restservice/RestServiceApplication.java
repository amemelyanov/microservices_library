package ru.job4j.restservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.job4j.restservice.client.BookClient;
import ru.job4j.restservice.wsdl.GetBookResponse;

@SpringBootApplication
public class RestServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner lookup(BookClient quoteClient) {
        return args -> {
            int id = 1;

            if (args.length > 0) {
                id = Integer.parseInt(args[0]);
            }
            GetBookResponse response = quoteClient.getBook(id);
            System.err.println(response.getBook().getName());
        };
    }

}
