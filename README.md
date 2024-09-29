# Case Study Application

This is a Spring Boot application designed for managing customers, assets, and orders. The application includes a login endpoint, order matching functionality, and is secured using basic authentication.

## Table of Contents
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Requirements](#requirements)
- [Installation and Setup](#installation-and-setup)
- [Build and Run the Project](#build-and-run-the-project)
- [API Documentation](#api-documentation)
- [Testing](#testing)

## Features
- Basic CRUD operations for Customers, Assets, and Orders.
- User login with basic authentication.
- Order matching functionality for admins.
- H2 in-memory database for testing and development.
- Role-based access control for secure endpoints.
- REST API endpoints for all operations.

## Technologies Used
- **Java 17**
- **Spring Boot** 3.x
- **Spring Security** for authentication and authorization
- **H2 Database** for in-memory data storage
- **Spring Data JPA** for ORM
- **Gradle** for project build and dependency management
- **JUnit** and **Mockito** for testing

## Requirements
- **Java 17** or higher
- **Gradle** (Optional: You can use the Gradle wrapper provided in the project)
- An IDE like IntelliJ IDEA, Eclipse, or Visual Studio Code

## Installation and Setup
1. **Clone the Repository:**
   ```bash
   git clone https://github.com/your-username/your-repository.git
   cd your-repository
2. **Open the Project in an IDE:**

Import the project as a Gradle project in your preferred IDE (e.g., IntelliJ IDEA, Eclipse).

## Build and Run the Project
1. **Build the Project:**
- Navigate to the project's root directory and run:
  ```bash
  ./gradlew clean build
- On Windows, use:
  ```bash
  .\gradlew clean build
2. **Run the Project:**
-  After the build is successful, run the application using the Gradle wrapper
   ```bash
   ./gradlew bootRun
- On Windows, use:
    ```bash
    .\gradlew bootRun
3. **Access the Application:**
-The application will start running on http://localhost:8080.
