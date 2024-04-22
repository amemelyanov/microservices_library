package ru.job4j.restservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;
import java.util.stream.Stream;

/**
 * Конфигурация для Security
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.restservice.dto.ListBookDto
 */
@Configuration
public class SecurityConfig {

    /**
     * Метод создает бин SecurityFilterChain, настраивает для параметра метода http, правила обработки
     * запросов клиента.
     *
     * @return объект фильтра для http запросов.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(new AntPathRequestMatcher("/v1/kafka/book/**"))
                        .hasAnyRole("USER", "ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/v1/soap/book/**"))
                        .hasAnyRole("USER", "ADMIN")
                        .anyRequest()
                        .authenticated());
        return http.build();
    }

    /**
     * Метод создает бин JwtAuthenticationConverter, получает из jwt-токена список ролей пользователя.
     *
     * @return объект jwtAuthenticationConverter.
     */
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        var converter = new JwtAuthenticationConverter();
        var jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        converter.setPrincipalClaimName("preferred_username");
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            var authorities = jwtGrantedAuthoritiesConverter.convert(jwt);
            var roles = (List<String>) jwt.getClaimAsMap("realm_access").get("roles");

            return Stream.concat(authorities.stream(),
                            roles.stream()
                                    .filter(role -> role.startsWith("ROLE_"))
                                    .map(SimpleGrantedAuthority::new)
                                    .map(GrantedAuthority.class::cast))
                    .toList();
        });
        return converter;
    }
}
