# Bank Card Management System

Java + Spring Boot приложение для управления банковскими картами пользователей. Реализованы создание, удаление, блокировка и активация карт, а также базовая система авторизации с JWT.

##  Технологии

- Java 17+
- Spring Boot
- Spring Security + JWT
- Hibernate + JPA
- PostgreSQL
- Liquibase (миграции БД)
- Maven
- JUnit + MockMvc (интеграционные тесты)

---

##  Локальный запуск

### 1. Клонируй репозиторий

```
git clone https://github.com/maximka9/test.git
cd test
```
### 2. Создай базу данных
Подключение по умолчанию ожидает PostgreSQL с такими параметрами:

properties

spring.datasource.url=jdbc:postgresql://localhost:5432/cards_db
spring.datasource.username=postgres
spring.datasource.password=your_password

### 3. Прогони миграции Liquibase (автоматически)
Liquibase запустится при старте Spring Boot и создаст нужные таблицы.

### 4. Запусти приложение
В IntelliJ IDEA или через терминал:

./mvnw spring-boot:run

mvn clean install
java -jar target/test-0.0.1-SNAPSHOT.jar
### 5. Swagger (если подключён)
После запуска приложение будет доступно на:
http://localhost:8080/swagger-ui/index.html
## Запуск тестов
mvn test
