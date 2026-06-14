# LotoLab Backend

Minimal Spring Boot backend foundation for the LotoLab project. It currently
provides a health endpoint and generated OpenAPI documentation.

## Prerequisites

- Java 25
- Maven 3.9 or later
- Docker with Docker Compose

## Local MongoDB

From the repository root, start MongoDB:

```shell
docker compose up -d
```

Stop MongoDB:

```shell
docker compose down
```

The named Docker volume is retained when the container stops.

Default connection details:

- Host: `localhost`
- Port: `27017`
- Database: `lotolab`
- Username: `lotolab`
- Password: `lotolab`
- Authentication database: `admin`
- URI: `mongodb://lotolab:lotolab@localhost:27017/lotolab?authSource=admin`

Set the `MONGODB_URI` environment variable to override the default connection.

## Run the application

Start MongoDB, then run from the `backend` directory:

```shell
mvn spring-boot:run
```

## Run the tests

The integration tests require MongoDB to be running.

```shell
mvn test
```

## API documentation

Once the application is running:

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

## Health endpoint

http://localhost:8080/api/health
