# Kafka Streams Demo Project with Spring Boot

This demo project showcases a simple Kafka Streams topology using Spring Boot. The application
processes input data, maps it to an output using a key-value store, and leverages the
`KafkaStreamsInteractiveQueryService` to interact with the KStreams store.

## Table of Contents

1. [Introduction](#introduction)
2. [Prerequisites](#prerequisites)
3. [Project Structure](#project-structure)
4. [Configuration](#configuration)
5. [Running the Application](#running-the-application)
6. [Key Components](#key-components)
7. [Conclusion](#conclusion)

## Introduction

This project demonstrates how to set up a Kafka Streams application using Spring Boot. It includes a
simple topology that reads input data, processes it using a key-value store, and produces output
data. The mapping logic is encapsulated in a repository service that utilizes
`KafkaStreamsInteractiveQueryService` to query the KStreams store.

## Prerequisites

Before running the application, ensure you have the following:

- Java 22 or higher
- Apache Kafka installed and running. It is possible to use the docker-compose file to run
  Kafka blocker, schema registry, and a Kafka UI.
- Maven for dependency management

## Project Structure

The project is structured as follows:

```
kafka-streams-demo/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/
│   │   │   │   ├── example/
│   │   │   │   │   ├── KafkaStreamsDemoApplication.java
│   │   │   │   │   ├── config/
│   │   │   │   │   │   ├── KafkaConfig.java
│   │   │   │   │   ├── topology/
│   │   │   │   │   │   ├── GlobalTables.java
│   │   │   │   │   │   ├── StreamTopology.java
│   │   │   │   │   ├── repository/
│   │   │   │   │   │   ├── CodesRepository.java
│   │   ├── resources/
│   │   │   ├── application.properties
├── pom.xml
├── docker-compose.yml
```

## Configuration

The application configuration is defined in the `application.yaml` file. It is configured to connect
to kafka broker configured in docker compose.

## Running the Application

To run the application, first start kafka server with

```bash
docker-compose up
```

Then use the following command:

```bash
mvn spring-boot:run
```

## Key Components

### KafkaStreamsDemoApplication

The main application class that initialises the Spring Boot application.

### KafkaConfig

Configuration class for setting up Kafka Streams.

### StreamTopology

It defines the topology and configures the streams.

### GlobalTables

Creates a global table and materialises it as a key value store that can be queried.

### CodesRepository

Repository class that uses `KafkaStreamsInteractiveQueryService` to interact with the KStreams
store and return the mapping value for a given code. Demonstrates how to use interactive queries to
retrieve data from the KStreams store. This is useful for querying the state of the stream
processing application in real-time.

## Conclusion

This demo project provides a basic example of how to set up a Kafka Streams application using Spring
Boot. It includes a simple topology, key-value store mapping, and uses
`KafkaStreamsInteractiveQueryService` to retrieve the queryable store. This can serve as a
foundation for more complex stream processing applications.
