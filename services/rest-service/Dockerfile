FROM maven:3.8.4-openjdk-17 as maven
WORKDIR /app
COPY . /app/.
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=maven /app/target/*.jar /app/rest-service.jar
EXPOSE 8080
CMD java -jar /app/rest-service.jar