package ru.job4j.gateway.filters;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Реализация фильтра отслеживания запросов
 *
 * @author Alexander Emelyanov
 * @version 1.0
 */
@Slf4j
@AllArgsConstructor
@Order(1)
@Component
public class TrackingFilter implements GlobalFilter {

    /**
     * Объект для доступа к методам FilterUtils
     */
    private final FilterUtils filterUtils;

    /**
     * Метод выполняет отслеживание запросов содержащих идентификатор корреляции. Для проверки наличия заголовка
     * с идентификатором корреляции используется метод {@link TrackingFilter#isCorrelationIdPresent(HttpHeaders)}.
     * Если идентификатор корреляции отсутствует, то он генерируется с помощью метода
     * {@link TrackingFilter#generateCorrelationId()} и добавляется в заголовки
     *
     * @param exchange объект HTTP запроса
     * @param chain цепочка фильтров
     * @return передает значение HTTP запроса следующему фильтру в цепочке
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
        if (isCorrelationIdPresent(requestHeaders)) {
            log.debug("tmx-correlation-id найден in tracking filter: {}. ",
                    filterUtils.getCorrelationId(requestHeaders));
        } else {
            String correlationID = generateCorrelationId();
            exchange = filterUtils.setCorrelationId(exchange, correlationID);
            log.debug("tmx-correlation-id сгенерирован in tracking filter: {}.", correlationID);
        }
        return chain.filter(exchange);
    }

    /**
     * Метод выполняет проверки содержат ли заголовки HTTP запроса идентификатор корреляции. Для проверки вызывается
     * метод утилитного класса {@link FilterUtils#getCorrelationId(HttpHeaders)}.
     *
     * @param requestHeaders заголовки запроса
     * @return результат проверки содержат ли заголовки идентификатор корреляции
     */
    private boolean isCorrelationIdPresent(HttpHeaders requestHeaders) {
        return filterUtils.getCorrelationId(requestHeaders) != null;
    }

    /**
     * Метод выполняет генерацию и возврат нового идентификатора корреляции.
     *
     * @return идентификатор корреляции
     */
    private String generateCorrelationId() {
        return java.util.UUID.randomUUID().toString();
    }

}