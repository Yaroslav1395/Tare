# Используем базовый образ с Maven для сборки
FROM maven:3.8.6-amazoncorretto-17 AS build
# Устанавливаем рабочую директорию
WORKDIR /tare
# Копируем pom.xml и файлы проекта для сборки
COPY pom.xml .
COPY src ./src
# Чистим и собираем проект
RUN mvn clean package -DskipTests
# Используем образ для запуска приложения с JRE
FROM amazoncorretto:17-alpine
# Копируем собранный JAR файл из предыдущего контейнера
COPY --from=build /tare/target/*.jar /tare.jar
# Устанавливаем команду для запуска приложения
ENTRYPOINT ["java", "-jar", "/tare.jar"]