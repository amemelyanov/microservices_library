package ru.job4j.libraryservice.service.cover;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.job4j.libraryservice.repository.CoverMinioRepository;

import java.io.File;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Тест класс реализации сервисов
 * @see ru.job4j.libraryservice.service.book.BookServiceImpl
 * @author Alexander Emelyanov
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
class CoverServiceMinioTest {

    /**
     * Константа имя файла
     */
    public static final String FILE_NAME = "filename";

    /**
     Моск объекта CoverMinioRepository
     */
    @Mock
    private CoverMinioRepository minioRepository;

    /**
     Объект File
     */
    private File file;

    /**
     * Выполняется проверка работы метода по загрузке файла в хранилище.
     */
    @Test
    void upload() {

        when(minioRepository.upload(file, FILE_NAME))
                .thenReturn(FILE_NAME);

        String actualResult = minioRepository.upload(file, FILE_NAME);
        String expectedResult = FILE_NAME;

        verify(minioRepository).upload(file, FILE_NAME);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    /**
     * Выполняется проверка работы метода по получению файла из хранилища,
     * если файл в хранилище присутствует.
     */
    @Test
    void getFileWhenFileExists() {
        Optional<byte[]> bytes = Optional.of("new byte[]".getBytes());
        when(minioRepository.getFile(FILE_NAME))
                .thenReturn(bytes);

        Optional<byte[]> actualResult = minioRepository.getFile(FILE_NAME);
        Optional<byte[]> expectedResult = bytes;

        verify(minioRepository).getFile(FILE_NAME);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    /**
     * Выполняется проверка работы метода по получению файла из хранилища,
     * если файл в хранилище отсутствует.
     */
    @Test
    void getFileWhenFileNotExist() {
        Optional<byte[]> empty = Optional.empty();
        when(minioRepository.getFile(FILE_NAME))
                .thenReturn(empty);

        Optional<byte[]> actualResult = minioRepository.getFile(FILE_NAME);
        Optional<byte[]> expectedResult = empty;

        verify(minioRepository).getFile(FILE_NAME);
        assertThat(actualResult).isEqualTo(expectedResult);
    }
}