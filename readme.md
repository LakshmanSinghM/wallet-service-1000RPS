# Wallet Service

A high-concurrency REST API for wallet management (deposit, withdraw, balance) 

## Tech Stack
- Java 17 · Spring Boot 3 · PostgreSQL 15 · Liquibase · Docker Compose

## Quick Start

**1.** Copy `.env.example` to `.env` and update values if needed.

**2. Start with Docker**
```bash
docker-compose up --build
```
App runs at `http://localhost:8080`.


but but first test the application then only run the test cases as it deletes the records from the db 

## Testing

 See /Test-application.md for full testing instructions and concurrency testing guide.