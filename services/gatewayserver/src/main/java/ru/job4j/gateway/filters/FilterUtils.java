package ru.job4j.gateway.filters;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

/**
 * Утилитный класс фильтров
 *
 * @author Alexander Emelyanov
 * @version 1.0
 */
@Component
public class FilterUtils {

    /**
     * Наименование заголовка идентификатора корреляции
     */
    public static final String CORRELATION_ID = "tmx-correlation-id";

    /**
     * Наименование заголовка токена аутентификации
     */
    public static final String AUTH_TOKEN = "tmx-auth-token";

    /**
     * Наименование заголовка идентификатора пользователя
     */
    public static final String USER_ID = "tmx-user-id";

    /**
     * Наименование заголовка типа пре фильтра
     */
    public static final String PRE_FILTER_TYPE = "pre";

    /**
     * Наименование заголовка типа пост фильтра
     */
    public static final String POST_FILTER_TYPE = "post";

    /**
     * Наименование заголовка типа фильтра маршрутизации
     */
    public static final String ROUTE_FILTER_TYPE = "route";

    /**
     * Метод выполняет получение заголовков запроса по идентификатору корреляции.
     *
     * @param requestHeaders заголовки запроса
     * @return значение идентификатора корреляции
     */
    public String getCorrelationId(HttpHeaders requestHeaders) {
        if (requestHeaders.get(CORRELATION_ID) != null) {
            List<String> header = requestHeaders.get(CORRELATION_ID);
            return header.stream().findFirst().get();
        } else {
            return null;
        }
    }

    /**
     * Метод выполняет добавление заголовка идентификатора корреляции в HTTP запрос.
     *
     * @param exchange объект HTTP запроса
     * @param name имя заголовка идентификатора корреляции
     * @param value значение идентификатора корреляции
     * @return объект HTTP запроса
     */
    public ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value) {
        return exchange.mutate().request(
                        exchange.getRequest().mutate()
                                .header(name, value)
                                .build())
                .build();
    }

    /**
     * Метод выполняет установку идентификатору корреляции с помощью метода
     * {@link FilterUtils#setRequestHeader(ServerWebExchange, String, String)}.
     *
     * @param exchange объект HTTP запроса
     * @param correlationId значение идентификатора корреляции
     * @return объект HTTP запроса
     */
    public ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationId) {
        return this.setRequestHeader(exchange, CORRELATION_ID, correlationId);
    }

}
