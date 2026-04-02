# Wallet Service Testing Plan

This document provides instructions on how to build, run, and verify the Wallet Service operations.

## Prerequisites
- **Java 17** (for local development/testing)
- **Maven**
- **Docker** and **Docker Compose**

---

## 1. Build and Run with Docker

To initiate the entire system (Application + Database):
 
docker-compose up --build

- The application will be available at `http://localhost:8080`.
- The PostgreSQL database will be running on port `5432`.
- Configuration can be modified in the `.env` file without rebuilding the containers.


ENV file Sample values 
<!-- # Application Config
SERVER_PORT=8080
LOG_LEVEL=INFO

# Database Config
POSTGRES_DB=wallet_db
POSTGRES_USER=postgres
POSTGRES_PASSWORD=root
DB_URL=jdbc:postgresql://db:5432/wallet_db
TEST_DB_URL=jdbc:postgresql://localhost:5432/wallet_db -->
 

## 2. Automated Testing

You can run the integration tests locally to verify all endpoints and error handling:
 using 

```bash
mvn test
```

The tests cover:
- Successful balance retrieval.
- Wallet not found (404).
- Successful Deposit/Withdraw operations.
- Insufficient funds (400).
- Invalid JSON and field validation (400).

---

## 3. Manual Testing (API Verification)

Use Postman to test the endpoints. A test wallet is pre-seeded in the migration: `550e8400-e29b-41d4-a716-446655440000`.

### A. Get Wallet Balance
**Endpoint**: `GET /api/v1/wallets/{WALLET_UUID}`

```bash
curl http://localhost:8080/api/v1/wallets/550e8400-e29b-41d4-a716-446655440000
```

**Expected Success Response**:
```json
{
  "data": { "balance": 10000.00 },
  "message": "Balance retrieved successfully",
  "success": true,
  "systemCode": "SUCCESS",
  "httpCode": "OK"
}
```

### B. Deposit Operation
**Endpoint**: `POST /api/v1/wallet`

```bash
curl -X POST http://localhost:8080/api/v1/wallet \
     -H "Content-Type: application/json" \
     -d '{
       "valletId": "550e8400-e29b-41d4-a716-446655440000",
       "operationType": "DEPOSIT",
       "amount": 500.50
     }'
```

### C. Withdraw Operation (Insufficient Funds)
```bash
curl -X POST http://localhost:8080/api/v1/wallet \
     -H "Content-Type: application/json" \
     -d '{
       "valletId": "550e8400-e29b-41d4-a716-446655440000",
       "operationType": "WITHDRAW",
       "amount": 9999999
     }'
```
**Expected Error Response**:
```json
{
  "data": null,
  "message": "Insufficient funds in wallet: 550e8400-e29b-41d4-a716-446655440000",
  "success": false,
  "systemCode": "INSUFFICIENT_FUNDS",
  "httpCode": "BAD_REQUEST"
}
```

---

## 4. Concurrency Testing (1000 RPS)

The application uses **Pessimistic Locking** (`SELECT FOR UPDATE`) to handle high-concurrency requests per wallet. 

To verify this manually, you can run multiple concurrent requests using a tool like `Apache Benchmark (ab)` or a simple bash loop:

```bash
# Example using Apache Benchmark for 1000 requests with 50 concurrency
ab -n 1000 -c 50 -p deposit.json -T application/json http://localhost:8080/api/v1/wallet
```

*Note: Create a `deposit.json` file with the request body before running `ab`.*

---

## 5. Cleaning Up

To stop and remove the containers:

```bash
docker-compose down
```