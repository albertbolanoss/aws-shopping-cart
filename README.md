# Shopping Cart 🛒

Shopping Cart is a sample code used to validate coding best practices using the Java language.

## Pre requirements
- Java 17 or greater and create the home Java environment variable
- Gradle [7.6.4](https://gradle.org/releases) or greater and create the Gradle home and Gradle Bin paths in environment variables
- Redis Server or use an [docker image](https://hub.docker.com/_/redis)

This project has the following configuration for redis
```bash
docker pull redis
docker run --name redis-server -d -p 6379:6379 redis --requirepass password
```

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

The system has preconfigured the following products (id, description)

| Id | Product |
|--|--|
| 379fbb01-ff84-4a61-8856-afa2d52b3974 | Banana     |
| 560a00dd-6864-49b9-b3f5-1ef420a0efca | Orange     |
| 2f04919e-b6a8-4235-ac96-69c1659fa965 | Strawberry |

The following is a list of the allowed operations in the API specification:

| Method | URI                              | Description                                         |
|--------|----------------------------------|-----------------------------------------------------|
| Get    | /api/v1/token/csrf               | Get the Cross-Site Request Forgery token            |
| POST   | /api/v1/token                    | Authetication                                       |
| POST   | /api/v1/product/{productId}/item | Add Item to Cart                                    |
| Delete | /api/v1/product/{productId}/item | Delete an Item from Cart                            |
| Get    | /api/v1/product/{productId}/item | Get the Items from the Cart                         |
| Delete | /api/v1/items                    | Delete all the Items from the Cart                  |
| POST   | /api/v1/checkout                 | Checkout the items in the cart                      |  
| POST   | /api/v1/user                     | Create a new user                                   |
| Get    | /api/v1/user                     | Get the user information filter by query parameters |

## Run the tests

To build execute the following command
```bash
./gradlew bootTestRun
```

## Project folder and key files description

- .github/workflow: Github Action workflow.
- aws: Infrastructure as code definition (AWS Cloud Formation)
- src/main/resources/application.yml: The spring application configuration.
- src/main/resources/api: Open API Specification definition.
- src/main/resources/config/liquibase:  Liquibase database change management.
- src/main/java/com/ecommerce/shared: the transversal or shared module 
- src/main/java/com/ecommerce/cart: the cart module.
- src/main/java/com/ecommerce/user: the user modules.
- src/test/resources/application.yml: the spring test configuration.
- src/test/java/com/ecommerce: the unit tests
- build.gradle: the grade build definition


## Next Steps 
- Integrate with OAUTH2.0 

## used Technologies. 

This project implemented the use of the following technologies

- Open API Specification (OAS) to API specification generate code,and document and Swagger interface
- Liquibase (database change management)
- H2 Database for application
- H2 Database for test (not yet implemented)
- Redis
- Lombok
- spring-boot-starter-web
- spring-boot-starter-data-jpa
- Gradle
- Github action workflow (to run CI in Github)
- AWS Integration

## Other commands

### How generating the public/private keys

```bash
keytool -genkey -alias "jwt-sign-key" -keyalg RSA -keystore jwt-keystore.jks -keysize 4096
```

The generated key store should be placed under the src/main/resources directory.

IMPORTANT NOTE

Public/private keys are valid only for 90 days from the time they are generated. Therefore, make sure that you create a new set of public/private keys before you run this chapter’s code.

To generate a key that last more time use validity parameter (days).  For instance to create for ten years
```bash
keytool -genkey -alias "jwt-sign-key" -keyalg RSA -keystore jwt-keystore.jks -keysize 4096 -validity 3650

# Delete previous keys
keytool -delete -alias "jwt-sign-key" -keystore jwt-keystore.jks

```

### How generate self-signed certificate
```bash
keytool -genkeypair -alias mykey -keyalg RSA -keysize 2048 -keystore keystore.jks -validity 3650
keytool -exportcert -alias mykey -keystore keystore.jks -rfc -file certificate.crt
keytool -importkeystore -srckeystore keystore.jks -destkeystore keystore.p12 -srcstoretype JKS -deststoretype PKCS12
```

### How configure AWS Secret Manager with Spring Boot Configuration

This project is using a secret from AWS Secret Manager service to set the environment variables

```sh
# application.yml
spring:
    config:
        import: optional:aws-secretsmanager:shopping-cart-config
```

#### References

- https://docs.awspring.io/spring-cloud-aws/docs/3.0.1/reference/html/index.html#spring-cloud-aws-secrets-manager
- https://www.youtube.com/watch?v=1j028KYS4ps

#### Run local using AWS Secret Manager

To run locally using the AWS Secret Manager, create the following enviroment variables:

```sh
export AWS_ACCESS_KEY_ID=???
export AWS_SECRET_ACCESS_KEY=???
```
#### How show secret information with AWS CLIs 
```sh
aws secretsmanager get-secret-value --secret-id shopping-cart-config
```

#### Create Secret
1. Create the secret in AWS Secret Manager service
1. Create policy and Role
1. Associate the IAM Role to the instance (Security/Modify IAM Role)

```sh
# Create the secret in AWS Secret Manager
aws secretsmanager create-secret --name shopping-cart-config --secret-string '{"SSL_KEY_PASSWORD":"?","REDIS_HOST":"?","REDIS_PASSWORD":"?","JWT_PASSWORD":"?","JWT_PASSPHRASE":"?"}'


# Policy
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Sid": "VisualEditor0",
            "Effect": "Allow",
            "Action": [
                "secretsmanager:GetSecretValue",
                "secretsmanager:DescribeSecret"
            ],
            "Resource": "arn:aws:secretsmanager:[Region]:[User ID]:secret:[Secret name]"
        }
    ]
}
```

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.
