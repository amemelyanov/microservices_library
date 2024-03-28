package ru.job4j.gateway.filters;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

/**
 * Конфигурация фильтра ответа
 *
 * @author Alexander Emelyanov
 * @version 1.0
 */
@Slf4j
@AllArgsConstructor
@Configuration
public class ResponseFilter {

    /**
     * Объект для доступа к методам FilterUtils
     */
    private final FilterUtils filterUtils;

    /**
     * Метод создает бин фильтра ответа.
     *
     * @return возвращает объект глобального фильтра
     */
    @Bean
    public GlobalFilter postGlobalFilter() {
        return (exchange, chain) -> {
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
                String correlationId = filterUtils.getCorrelationId(requestHeaders);
                log.debug("Добавление correlation id в исходные headers. {}", correlationId);
                exchange.getResponse().getHeaders().add(FilterUtils.CORRELATION_ID, correlationId);
                log.debug("Завершение исходщего запроса for {}.", exchange.getRequest().getURI());
            }));
        };
    }
}
