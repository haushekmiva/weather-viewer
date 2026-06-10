# Weather Viewer

Веб-приложение для отслеживания погоды в сохранённых локациях. Пятый проект из [роадмапа Сергея Жукова](https://zhukovsd.github.io/java-backend-learning-course/).

## Функциональность

- Регистрация и авторизация пользователей
- Поиск локаций через OpenWeather Geocoding API
- Добавление и удаление локаций
- Отображение текущей погоды для каждой сохранённой локации
- Сессионная аутентификация на основе UUID-куки

## Технологии

- **Java 24**
- **Spring MVC** (без Spring Boot, Java-конфигурация)
- **Hibernate ORM**
- **MySQL**
- **Flyway** — миграции базы данных
- **HikariCP** — пул соединений
- **Thymeleaf** — шаблонизатор
- **WebClient** — HTTP-клиент для OpenWeather API
- **MapStruct** — маппинг DTO
- **Lombok**
- **Maven**
- **JUnit 5 + AssertJ + MockWebServer** — тесты

## Запуск локально

### Требования

- Java 24+
- Maven
- MySQL
- API-ключ [OpenWeatherMap](https://openweathermap.org/api)

### 1. Клонировать репозиторий

```bash
git clone https://github.com/haushekmiva/weather-viewer.git
cd weather-viewer
```

### 2. Создать базу данных

```sql
CREATE DATABASE weather_viewer;
```

### 3. Настроить `application.properties`

Создать файл `src/main/resources/application.properties` по примеру:

```properties
#DB
db.url=jdbc:mysql://localhost:3306/weather_viewer
db.username=your_username
db.password=your_password
db.pool.size=10
db.driver.class_name=com.mysql.cj.jdbc.Driver

#Hibernate
hibernate.hbm2ddl.auto=validate
hibernate.show_sql=false
hibernate.format_sql=false

#Session
session.max_age_seconds=604800

#API
open_weather.api.key=your_openweather_api_key
```

### 4. Собрать и задеплоить

```bash
mvn clean package
```

Задеплоить полученный `.war` из `target/` на Tomcat 11+.

Flyway автоматически применит миграции при запуске.