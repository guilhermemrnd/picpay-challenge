# PicPay Challenge Application

This project is a solution to the PicPay Fintech backend challenge. It involves creating an application for managing digital wallets and transferring money between them while adhering to business rules.

## Features

- **Wallet Management**: Create user and merchant wallets.
- **Money Transfers**:
  - Merchants can only receive money, not transfer it.
  - Transfers are validated to ensure the amount does not exceed the current balance.
  - Transactions are authorized by an external mocked API.
  - Notifications are sent asynchronously via an external API after successful transactions.

## Technologies Used

- **Java 21** with **Spring Boot** for the backend application.
- **PostgreSQL** as the relational database.
- **Docker** for containerization and environment setup.
- **Testcontainers** for integration testing.

## Architecture

The project follows **Clean Architecture**, adhering to the following principles:

- **Domain Layer**: Contains core business logic, domain entities, and custom domain errors.
- **Application Layer**: Implements use cases and orchestrates interactions between the domain and infrastructure.
- **Infrastructure Layer**: Handles external concerns such as database access and external API integrations.
- **Dependency Inversion**: Ensures that dependencies flow from the outer layers to the inner layers, meaning that infrastructure concerns rely on abstractions defined in the domain layer.

Domain errors are modeled at the appropriate level, bubbling up to a global exception handler to return meaningful responses to clients.

## Testing Strategy

- **Unit Tests**: Focus on testing the core business logic in isolation.
- **Integration Tests**: Use the Testcontainers library to validate interactions with external dependencies such as PostgreSQL and external APIs.

## Endpoints

1. **Wallets**:
   - **POST /wallets**: Create a new wallet (user or merchant).
   
2. **Transfers**:
   - **POST /transfers**: Transfer money between wallets.

## Setup Instructions

### Prerequisites

- **Docker**: Ensure Docker is installed for container management.
- **Java 21**: Required to run the application.
- **Maven**: For building the project.

### Clone the Repository

```bash
git clone https://github.com/guilhermemrnd/picpay-challenge.git
cd picpay-challenge
```

### Configure the Environment File

Create a `.env` file in the project root with the following variables:

```env
PORT=8080

# Database
DB_URL=jdbc:postgresql://localhost:5432/picpaydb
DB_NAME=picpaydb
DB_USERNAME=admin
DB_PASSWORD=admin
```

### Start Dependent Services

No manual setup of dependent services is required. The application uses the Spring Boot Docker Compose library, which automatically manages the lifecycle of Docker containers for the PostgreSQL database during startup.

### Build and Run the Application

Build and run the application with Maven:

```bash
mvn clean install
mvn spring-boot:run
```

### Verify Setup

- Ensure the application is running at `http://localhost:8080`.
- Test the endpoints using tools like Postman or cURL.

## Summary

This project demonstrates a robust wallet management and transaction system adhering to clean architecture principles, with a focus on scalability, testability, and adherence to business rules.