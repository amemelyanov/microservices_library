package ru.job4j.restservice.exception;

/**
 * Исключение выбрасываемое при отсутствии книги в базе
 * при выполнении поиска по идентификатору
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.restservice.model.Book
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Конструктор
     *
     * @param message сообщение выброшенного исключения
     */
    public ResourceNotFoundException(
            final String message
    ) {
        super(message);
    }

}
