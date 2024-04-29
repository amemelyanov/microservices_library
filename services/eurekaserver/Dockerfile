FROM maven:3.8.4-openjdk-17 as maven
WORKDIR /app
COPY . /app/.
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=maven /app/target/*.jar /app/eurekaserver.jar
EXPOSE 8070
CMD java -jar /app/eurekaserver.jar