package ru.job4j.libraryservice.service.cover;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.job4j.libraryservice.repository.CoverMinioRepository;

import java.io.File;
import java.util.Optional;

/**
 * Реализация сервиса по работе с картинками обложек книг
 *
 * @author Alexander Emelyanov
 * @version 1.0
 */
@RequiredArgsConstructor
@Service
public class CoverServiceMinio implements CoverService {

    /**
     * Объект для доступа к методам MinioRepository
     */
    private final CoverMinioRepository minioRepository;

    /**
     * Метод получает файл и имя и передает их на слой репозитория
     * через вызов метода {@link CoverMinioRepository#upload(File, String)}.
     * Возвращает имя файла.
     *
     * @param file файл
     * @param name имя файла
     * @return имя файла
     */
    @Override
    public String upload(File file, String name) {
        return minioRepository.upload(file, name);
    }

    /**
     * Метод получает наименование файла и возвращает изображение обложки в виде
     * массива байт. Для получения изображения обложки вызывается метод уровня
     * репозитория {@link CoverMinioRepository#getFile(String)} с параметром имени файла.
     *
     * @param name наименование файла
     * @return массив байтов содержащих изображение
     */
    @Override
    public Optional<byte[]> getFile(String name) {
        return minioRepository.getFile(name);
    }

}
