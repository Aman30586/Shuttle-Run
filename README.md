Shuttle Run is a full-stack **Cab Booking System** designed using a microservices architecture. It supports three user roles: **Admin**, **Driver**, and **Customer**. Built with **Java**, **Spring Boot**, **MongoDB**, and **Angular**, this project demonstrates secure, scalable, and modular service-based application development.

## Tech Stack

- **Backend**: Java, Spring Boot, Spring Security, Spring Cloud (Eureka, Gateway), REST APIs  
- **Frontend**: Angular, HTML, CSS, JavaScript  
- **Database**: MongoDB  
- **Architecture**: Microservices  
- **Testing**: JUnit, JaCoCo  
- **Tools**: Git, Postman, Log4j

---

## Key Features

-  **JWT Authentication** for secure login and access control  
-  **Role-Based Access** (Admin registers drivers, customers can book rides)  
-  **Spring Cloud Gateway** for routing and filtering APIs  
-  **Eureka Server** for service discovery  
-  **Backend Validations** with UI toaster notifications  
-  **90%+ Test Coverage** using JUnit and JaCoCo  
-  Modular microservices with clear separation of concerns  

---

## Project Structure

shuttle-run/ 
  ├── discovery-server/ # Eureka Server
  ├── api-gateway/ # Spring Cloud Gateway
  ├── auth-service/ # Authentication and JWT
  ├── driver-service/ # Driver module
  ├── customer-service/ # Customer module
  ├── admin-service/ # Admin module
  ├── booking-service/ # Booking logic
  ├── frontend/ # Angular Frontend
  └── common/ # Shared models/utilities

## Getting Started

### Prerequisites
- Java 17+
- Node.js & Angular CLI
- MongoDB
- Maven
