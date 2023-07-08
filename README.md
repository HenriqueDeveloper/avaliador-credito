<p align="center">
<img src="https://picocli.info/images/spring-boot.png" alt="spring and spring boot logos" height="150px">
</p>

<p align="center">
  <img src="https://img.shields.io/badge/spring--boot-3.1.1-brightgreen.svg" alt="Spring Boot">
  <img src="https://img.shields.io/badge/java-17-brightgreen.svg" alt="Java">
</p>

# AVALIADOR-CREDITO-MICROSSERVICES

The Credit Evaluation API is a simple system developed to perform credit analyses based on card, client, and credit evaluator information. This API provides a user-friendly interface for conducting credit inquiries and obtaining quick responses regarding a client's eligibility to receive credit.

## Requirements

For building and running the application you need:

- [JDK 17](https://www.oracle.com/java/technologies/downloads/#java17)
- [Maven 3](https://maven.apache.org)
- [Postgres](https://www.postgresql.org/)

## Running the application with docker
```bash
docker-compose up or docker compose up
```

## Importing realm in keycloak

1. Access the address `http://localhost:8080` in your browser
2. Use the credentials `admin/admin` to log in
3. Once logged in, navigate to the "Realm Settings" option in the left-hand menu
4. In the submenu, select the "Partial Import" option
5. Choose the file `realm-export.json` located in the root of your project
6. Click the "Import" button to initiate the import process

Make sure you have the `realm-export.json` file ready in your project directory before starting the import.

## Creating queue in RabbitMQ

1. Access the address `http://localhost:15672` in you browser.
2. Use the credentials `guest/guest` to log in
3. Once logged in, navigate to the `Queues` section.
4. In the `Add a new queue` section, enter `emissao-cartoes` as the name for the queue.
5. Click on the `Create` button to create the queue.

## Swagger documentation

http://localhost:8888/swagger-ui.html

![Captura de tela de 2023-07-08 15-32-52](https://github.com/c-henrique-dev/avaliador-credito-microsservice/assets/70810148/96b8a2df-f503-45e6-89ed-903b399be9c2)
