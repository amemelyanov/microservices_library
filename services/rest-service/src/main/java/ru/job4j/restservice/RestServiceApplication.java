package ru.job4j.restservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * Основной класс для запуска приложения
 *
 * @author Alexander Emelyanov
 * @version 1.0
 */
@RefreshScope
@SpringBootApplication
@EnableDiscoveryClient
public class RestServiceApplication {

    /**
     * Метод выполняет запуск приложения
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        SpringApplication.run(RestServiceApplication.class, args);
    }

}
