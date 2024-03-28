package ru.job4j.libraryservice.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация для Minio
 *
 * @author Alexander Emelyanov
 * @version 1.0
 */
@Configuration
public class MinioConfig {

    /**
     * Имя пользователя для доступа к Minio
     */
    @Value("${minio.access.name}")
    String accessKey;

    /**
     * Пароль для доступа к Minio
     */
    @Value("${minio.access.secret}")
    String accessSecret;

    /**
     * URL для доступа к Minio
     */
    @Value("${minio.url}")
    String minioUrl;

    /**
     * Метод создает бин клиента для доступа к Minio.
     *
     * @return возвращает клиента для доступа к Minio
     */
    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(minioUrl)
                .credentials(accessKey, accessSecret)
                .build();
    }
}