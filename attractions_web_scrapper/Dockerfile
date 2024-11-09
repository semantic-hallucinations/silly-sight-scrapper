# Используем образ с JDK и Chrome
FROM selenium/standalone-chrome:latest

# Устанавливаем JDK
FROM openjdk:22

# Создаем рабочую директорию
WORKDIR /app

# Копируем JAR-файл в рабочую директорию контейнера
COPY target/attractions_web_scrapper-1.0-SNAPSHOT.jar /app/attractions_web_scrapper.jar

# Команда для запуска JAR-файла с использованием Selenium и Chrome
ENTRYPOINT ["java", "-jar", "attractions_web_scrapper.jar"]
