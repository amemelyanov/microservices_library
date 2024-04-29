[![Java CI with Maven](https://github.com/amemelyanov/microservices_library/actions/workflows/maven.yml/badge.svg)](https://github.com/amemelyanov/microservices_library/actions/workflows/maven.yml)

# microservices-library

## <p id="contents">Оглавление</p>

<ul>
<li><a href="#01">Описание проекта</a></li>
<li><a href="#02">Стек технологий</a></li>
<li><a href="#03">Требования к окружению</a></li>
<li><a href="#04">Сборка и запуск проекта</a>
    <ol type="1">
        <li><a href="#0401">Сборка проекта</a></li>
        <li><a href="#0402">Запуск проекта</a></li>
    </ol>
</li>
<li><a href="#05">Взаимодействие с приложением</a>
    <ol  type="1">
        <li><a href="#0501">Регистрация сайта</a></li>
        <li><a href="#0502">Авторизация</a></li>
        <li><a href="#0503">Регистрация URL</a></li>
        <li><a href="#0504">Переадресация</a></li>
        <li><a href="#0505">Статистика</a></li>
    </ol>
</li>
<li><a href="#06">Запуск проекта с использованием Docker</a>
<ol  type="1">
        <li><a href="#0601">Клонирование проекта</a></li>
        <li><a href="#0602">Сборка проекта в Docker image</a></li>
        <li><a href="#0603">Запуск проекта с использованием docker-compose</a></li>
        <li><a href="#0604">Работа с проектом</a></li>
</ol>
<li><a href="#contacts">Контакты</a></li>
</ul>

## <p id="01">Описание проекта</p>

Сервис REST API, содержащий информацию о библиотечных книгах. 
Сервис разбит на микросервисы, которые в свою очередь делятся на группы:
 - Инфраструктурные микросервисы:
    1. Spring Cloud Configuration Server - сервис конфигурации. 
    2. Spring Cloud Eureka - сервис регистрации  обнаружения сервисов.
    3. Spring Cloud Gateway - сервис шлюза.
 - Микросервисы выполняющие запросы клиента:
    1. Rest service - сервис взаимодествия с клиентами.
    2. Library service - сервис работы с СУБД и хранилищем данных (PostgreSQL, Minio).
 - Хранилища данных:
    1. PostgreSQL - реляционное СУБД, хранит данные о книгах.
    2. Minio - хранилище обложек книг.
 - Брокер сообщений:
    1. ZooKeper - служба для координации работы брокера сообщений.
    2. Kafka - брокер сообщений.

Функционал:
- Аутентификация и авторизация с использованием Spring Security и Keycloak.
- Получение информации по всем книгам сервиса.
- Получение информации по книге и по обложке по id книги.

В приложении реализованы два вида endpoints, которые получают данные из внутреннего 
сервиса library-service:
- с ипользованием Kafka `http://localhost:8072/library/v1/kafka/*` 
- с использованием SOAP `http://localhost:8072/library/v1/soap/*`.

<p><a href="#contents">К оглавлению</a></p>

## <p id="02">Стек технологий</p>

- Java 17
- Spring Boot 3, Spring Cloud
- Spring Security, Keycloak 
- Spring Data, PostgreSQL 15, Minio, Liquibase
- Maven, Lombok 
- Kafka, ZooKeeper, SOAP
- Docker, Docker Compose
- Javadoc, Checkstyle

<p><a href="#contents">К оглавлению</a></p>

## <p id="03">Требования к окружению</p>

Java 17, Maven 3.8, Docker

<p><a href="#contents">К оглавлению</a></p>

## <p id="04">Сборка и запуск проекта</p>

### <p id="0401">1. Сборка проекта</p>

Отдельно взятый сервис собирается в jar командой
`mvn clean package -DskipTests`

<p><a href="#contents">К оглавлению</a></p>

### <p id="0402">2. Запуск проекта</p>

Сборка и запуск проекта производится из корня проекта командой.
`docker-compose up`

<p><a href="#contents">К оглавлению</a></p>

## <p id="05">Взаимодействие с приложением</p>

### <p id="0501">1. Получение токена доступа</p>

В приложении создана конфигурация для Keycloak, которая при развертывании в Docker, автоматически 
импортируется. Импортируемые параметры конфигурации:

| Параметр конфигурации |   Значение    |
|:---------------------:|:-------------:|
|         realm         | library-realm |
|       client_id       |    library    |
|         user1         |   lib-admin   |
|         user2         |   lib-user    |
|         role1         |     ADMIN     |
|         role2         |     USER      |

Для получения токена доступа необходимо направить на адрес сервиса Keycloak,  
`http://keycloak:8080/realms/library-realm/protocol/openid-connect/token`
заполнив параметры:

| Параметр запроса | Значение |
|:----------------:|:--------:|
|    grant_type    | password |
|    client_id     | library  |
|     username     | lib-user |
|     password     | password |

Пример:

![alt text](img/img_1.jpg)

В ответ Keycloak предоставит access_token и refresh_token. Access_token 
необходимо использовать при выполнении запросов в качестве Bearer Token.
Refresh_token необходим для обновления access_token по истечению срока его
действия.

<p><a href="#contents">К оглавлению</a></p>

### <p id="0502">2. Запросы на получение информации</p>

Получение всех книг:
`http://localhost:8072/library/v1/kafka/book/all`

![alt text](img/img_2.jpg)

Получение книги по id:
`http://localhost:8072/library/v1/kafka/book/1`

![alt text](img/img_3.jpg)


Получение обложки книги по id:
`http://localhost:8072/library/v1/kafka/book/1/cover`

![alt text](img/img_4.jpg)

<p><a href="#contents">К оглавлению</a></p>

## <p id="06">Запуск проекта с использованием Docker Compose</p>

### <p id="0601">1. Клонирование проекта</p>

В CLI выполнить команду - `https://github.com/amemelyanov/microservices_library.git`. 
В текущей директории будет создана папка microservices_library, содержащая проект.

<p><a href="#contents">К оглавлению</a></p>

### <p id="0602">2. Сборка проекта в Docker Compose</p>
Для сборки проета используется команда - `docker-compose build`

<p><a href="#contents">К оглавлению</a></p>

### <p id="0603">3. Запуск проекта с использованием Docker Compose</p>

Для запуска собранного проекта используется команда - `docker-compose start`,
для остановки проекта - `docker-compose stop`.

<p><a href="#contents">К оглавлению</a></p>

## <p id="contacts">Контакты</p>

[![alt-text](https://img.shields.io/badge/-telegram-grey?style=flat&logo=telegram&logoColor=white)](https://t.me/T_AlexME)
&nbsp;&nbsp;
[![alt-text](https://img.shields.io/badge/@%20email-005FED?style=flat&logo=mail&logoColor=white)](mailto:amemelyanov@yandex.ru)
&nbsp;&nbsp;

<p><a href="#contents">К оглавлению</a></p>