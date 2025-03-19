# Calories Tracker API

## Описание
Простое API для учета калорий пользователей. Позволяет добавлять пользователей, блюда и фиксировать приёмы пищи.

## Стек технологий
- Java 17
- Spring Boot 3.4.3
- PostgreSQL
- Hibernate
- Lombok
- JUnit
- SpringDoc OpenAPI (Swagger)

## Запуск проекта
1. Убедись, что у вас установлен PostgreSQL. 
2. При первоначальном запуске выполните SQL запрос создания базы данных из `src/main/resources/start_data.sql`
2. В `application.properties` укажите настройки подключения к БД.
3. Соберите JAR-файл:
   ```sh
   mvn clean package
   ```
4. Запустите приложение:
   ```sh
   java -jar target/CountingCalories-0.0.1.jar
   ```
5. API будет доступно по адресу `http://localhost:8080`.
6. Swagger UI: `http://localhost:8080/docs`

## Описание эндпоинтов

### **Пользователи**
- `GET /users` - Получить всех пользователей
- `GET /users/{id}` - Получить пользователя по ID
- `POST /users` - Создать пользователя
- `PUT /users/{id}` - Редактировать пользователя
- `DELETE /users/{id}` - Удалить пользователя
- `GET /users/{id}/calories` - Получить дневную норму калорий пользователя
- `GET /users/{id}/eatings/check_calories?date=YYYY-MM-DD` - Проверить норму потребленных калорий за день
- `GET /users/{id}/eatings?date=YYYY-MM-DD` - Получить приемы пищи пользователя за день
- `GET /users/{id}/eatings/history?page={page}&size={size}&start_date=YYYY-MM-DD&end_date=YYYY-MM-DD` - Получить историю приемов пищи пользователя

### **Приемы пищи**
- `GET /eatings` - Получить все приемы пищи
- `GET /eatings/{id}` - Получить прием пищи по ID
- `POST /eatings` - Добавить прием пищи
- `PUT /eatings/{id}` - Редактировать прием пищи
- `DELETE /eatings/{id}` - Удалить прием пищи


### **Блюда**
- `GET /dishes` - Получить все блюда
- `GET /dishes/{id}` - Получить блюдо по ID
- `POST /dishes` - Добавить блюдо
- `PUT /dishes/{id}` - Редактировать блюдо
- `DELETE /dishes/{id}` - Удалить блюдо


## Тестирование
В проекте есть юнит-тесты, проверяющие:
- Корректный расчёт калорий за день по формуле Харриса-Бенедикта для мужчин / женщин / набора веса / уменьшения веса.

Запуск тестов:
```sh
mvn test
```

## Swagger
Документация API доступна в Swagger UI по адресу:
`http://localhost:8080/docs`

В директории проекта вы также можете найти коллекцию методов API для Postman: `src/main/resources/postman_collection.json`

Либо можно импортировать JSON в Postman из api-docs:
1. Открыть Postman, выбрать **Import**.
2. Вставить ссылку `http://localhost:8080/v3/api-docs`.
3. Нажать **Import** — появится коллекция с методами API.
