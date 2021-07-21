FROM openjdk:8-jdk-alpine as build

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw install -DskipTests
RUN ./mvnw package spring-boot:repackage
#RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM openjdk:8-jdk-alpine

EXPOSE 8080

COPY --from=build /target/*.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]