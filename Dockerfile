# Используем официальное Java 17 JDK изображение как базовое
FROM openjdk:17-jdk-slim

# Указываем рабочую директорию в контейнере
WORKDIR /app

# Копируем собранный jar-файл приложения
COPY target/test-0.0.1-SNAPSHOT.jar app.jar

# Запуск приложения
ENTRYPOINT ["java", "-jar", "app.jar"]
