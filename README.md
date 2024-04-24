# Shopping Cart 🛒

This project is a lab that simulates a shopping cart that will be deployed in a distributed way and will be scalable and available and reliable and deployed on Amazon Web Service.

## Pre requirements
- Java 17 or greater and create the home Java environment variable
- Gradle [7.6.4](https://gradle.org/releases) or greater and create the Gradle home and Gradle Bin paths in environment variables

## Installation

This repository doesn't include the Gradle wrapper so if you prefer to run this project using the wrapper
you have downloaded and configured gradle and created the wrapper for the first time.

```bash
./gradle wrapper
```

## Build

To build execute the following command
```bash
./gradlew clean build
```

## Run

To build execute the following command
```bash
./gradlew bootRun
```
Then you can access to [swagger api specification](http://localhost:8080/swagger-ui/index.html):
