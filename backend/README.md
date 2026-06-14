# LotoLab Backend

Minimal Spring Boot backend foundation for the LotoLab project. It currently
provides a health endpoint and generated OpenAPI documentation.

## Prerequisites

- Java 25
- Maven 3.9 or later

## Run the application

```shell
mvn spring-boot:run
```

## Run the tests

```shell
mvn test
```

## API documentation

Once the application is running:

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

## Health endpoint

http://localhost:8080/api/health
