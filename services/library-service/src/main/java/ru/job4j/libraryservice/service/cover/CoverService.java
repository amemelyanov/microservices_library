package ru.job4j.libraryservice.service.cover;

import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Optional;

/**
 * Сервис по работе с картинками обложек книг
 *
 * @author Alexander Emelyanov
 * @version 1.0
 */
@Service
public interface CoverService {

    /**
     * Метод получает файл и имя и загружает его в хранилище Minio.
     *
     * @param file файл
     * @param name имя файла
     * @return имя файла
     */
    String upload(File file, String name);


    /**
     * Метод получает наименование файла и возвращает изображение обложки в виде
     * массива байт.
     *
     * @param name наименование файла
     * @return массив байтов содержащих изображение
     */
    Optional<byte[]> getFile(String name);
}
