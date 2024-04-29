package ru.job4j.libraryservice.listener;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.job4j.libraryservice.service.cover.CoverServiceMinio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Компонент выполняющий загрузку обложек книг, используемых в качестве примера
 *
 * @author Alexander Emelyanov
 * @version 1.0
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class LoadDataOnStartUp {

    /**
     * Объект для доступа к методам MinioService
     */
    private final CoverServiceMinio minioService;

    /**
     * Параметр указывающий на необходимость загрузки обложек
     */
    @Value("${minio.preload-data.status}")
    private boolean preloadDataStatus;

    /**
     * Путь относительно resources расположения обложек
     */
    @Value("${minio.preload-data.path}")
    private String preloadDataPath;

    /**
     * Список обложек
     */
    @Value("#{'${minio.preload-data.img-list}'.split(';')}")
    private List<String> listOfImages;

    /**
     * Метод загружает список обложек книг в соответствии с настройками. Для преобразования потока данных
     * в файлы используется метод {@link LoadDataOnStartUp#inputStreamToFile(InputStream)},
     * для загрузки файлов в Minio используется метод {@link CoverServiceMinio#upload(File, String)}.
     * Если обложка с таким именем в Minio существует, загрузка не выполняется.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void uploadData() {
        if (preloadDataStatus) {
            ClassLoader classLoader = LoadDataOnStartUp.class.getClassLoader();
            for (String fileName : listOfImages) {
                Path path = Paths.get(preloadDataPath, fileName);
                try (InputStream inputStream = classLoader.getResourceAsStream(path.toString())) {
                    if (inputStream == null) {
                        continue;
                    }
                    File file = inputStreamToFile(inputStream);
                    if (minioService.getFile(fileName).isEmpty()) {
                        minioService.upload(file, fileName);
                    }

                } catch (IOException | NullPointerException e) {
                    log.error("Загрузка предустановленной обложки {} неудачна", fileName, e);
                }
            }
        }
    }

    /**
     * Метод преобразовывает входной поток данных с обложкой книги в файл и возвращает его.
     *
     * @param inputStream входной поток данных
     * @return файл с обложкой
     */
    @SneakyThrows
    private File inputStreamToFile(InputStream inputStream) {
        File file = File.createTempFile("file", "tmp");
        file.deleteOnExit();
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            inputStream.transferTo(outputStream);
        } catch (IOException e) {
            log.error("Преобразование входного потока данных в файл неудачно", e);
        }
        return file;
    }

}
