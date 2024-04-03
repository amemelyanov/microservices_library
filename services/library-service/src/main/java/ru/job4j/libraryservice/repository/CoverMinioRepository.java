package ru.job4j.libraryservice.repository;

import io.minio.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Optional;

/**
 * Хранилище картинок обложек книг в Minio
 *
 * @author Alexander Emelyanov
 * @version 1.0
 */
@Service
@Slf4j
public class CoverMinioRepository {

    /**
     * Объект для доступа к методам MinioClient
     */
    private final MinioClient minioClient;

    /**
     * Наименование бакета в Minio
     */
    @Value("${minio.bucket.name}")
    private String bucket;

    /**
     * Конструктор
     *
     * @param minioClient клиент Minio
     */
    public CoverMinioRepository(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    /**
     * Метод получает файл и имя и загружает его в хранилище Minio.
     * Возвращает имя файла. Если корзина в хранилище отсутствует, метод создает ее
     * с помощью метода {@link CoverMinioRepository#createBucket()}. Загрузка выполняется
     * с помощью метода {@link CoverMinioRepository#saveImage(InputStream, String)} ()}.
     *
     * @param file файл
     * @param name имя файла
     * @return имя файла
     */
    public String upload(File file, String name) {
        try {
            createBucket();
        } catch (Exception e) {
            log.error("Создание бакета неудачно", e);
        }
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (Exception e) {
            log.error("Загрузка обложки неудачна", e);
        }
        saveImage(inputStream, name);
        return name;
    }


    /**
     * Метод получает наименование файла и возвращает изображение обложки в виде
     * массива байт.
     *
     * @param name наименование файла
     * @return массив байтов содержащих изображение
     */
    public Optional<byte[]> getFile(String name) {
        try (InputStream stream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucket)
                        .object(name)
                        .build())) {
            byte[] content = IOUtils.toByteArray(stream);
            return Optional.of(content);
        } catch (Exception e) {
            log.error("Обложка с именем {} отсутствует", name, e);
        }
        return Optional.empty();
    }

    /**
     * Метод создает корзину (bucket) в хранилище Minio, если бакет отсутствует.
     */
    @SneakyThrows
    private void createBucket() {
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder()
                .bucket(bucket)
                .build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucket)
                    .build());
        }
    }

    /**
     * Метод получает входящий поток байтов и имя файла и производит загрузку хранилище данных Minio.
     *
     * @param inputStream входящий поток байтов
     * @param name имя файла
     */
    @SneakyThrows
    private void saveImage(final InputStream inputStream,
                           final String name) {
        minioClient.putObject(PutObjectArgs.builder()
                .stream(inputStream, inputStream.available(), -1)
                .bucket(bucket)
                .object(name)
                .build());
    }

}
