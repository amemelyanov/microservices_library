package ru.job4j.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Основной класс для запуска приложения
 *
 * @author Alexander Emelyanov
 * @version 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayServerApplication {

    /**
     * Метод выполняет запуск приложения
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayServerApplication.class, args);
    }

}
