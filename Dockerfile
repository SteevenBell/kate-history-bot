# Базовый образ с OpenJDK 17
FROM openjdk:17-jdk-slim
WORKDIR /app

# Копируем JAR-файл
COPY target/kate-history.jar app.jar

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "app.jar"]