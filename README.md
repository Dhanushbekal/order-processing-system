# Order Processing System

## Overview
A Spring Boot backend system for processing e-commerce orders. Customers can place orders, track their status, and perform basic order operations. The system includes scheduled background processing and is fully tested.

---

## Features
- Create an order with multiple items
- Retrieve order details by order ID
- Update order status (PENDING, PROCESSING, SHIPPED, DELIVERED, CANCELED)
- Background job: Automatically updates PENDING orders to PROCESSING every 5 minutes
- List all orders, optionally filtered by status
- Cancel an order (only if PENDING)
- RESTful API
- In-memory H2 database with web console
- Unit and integration tests

---

## Tech Stack
| Technology      | Version    |
|----------------|-----------|
| Java           | 17+        |
| Spring Boot    | 3.5.0      |
| Spring Data JPA| 3.5.0      |
| H2 Database    | 2.2.224    |
| Lombok         | 1.18.32    |
| Maven          | 3.8+       |
| JUnit 5        | 5.9+       |

---

## Setup & Installation
1. **Clone the repository:**
   ```sh
   git clone <your-repo-url>
   cd order-processing-system
   ```
2. **Build the project:**
   ```sh
   mvn clean install
   ```
3. **Run the application:**
   ```sh
   mvn spring-boot:run
   ```
   The app will start on [http://localhost:8080](http://localhost:8080) by default.

---

## API Endpoints
| Method | Endpoint                        | Description                        |
|--------|---------------------------------|------------------------------------|
| POST   | /api/orders                     | Create a new order                 |
| GET    | /api/orders                     | List all orders (optionally filter by status) |
| GET    | /api/orders/{id}                | Get order details by ID            |
| POST   | /api/orders/{id}/cancel         | Cancel an order (if PENDING)       |
| PATCH  | /api/orders/{id}/status         | Update order status                |

### Example: Create Order
```json
POST /api/orders
{
  "customerName": "Alice",
  "items": [
    { "productName": "Book", "quantity": 1, "price": 15.0 }
  ]
}
```

### Example: Update Status
```json
PATCH /api/orders/1/status
{
  "status": "SHIPPED"
}
```

---

## Database Access (H2 Console)
- URL: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- JDBC URL: `jdbc:h2:mem:ordersdb`
- User: `sa`
- Password: *(leave blank)*

---

## Running Tests
```sh
mvn test
```

---

## Screenshots
Add screenshots of:
- Application running in terminal
- API requests/responses (Postman/curl)
- H2 console with data
- Test results

Example:
```markdown
![App Running](screenshots/app-running.png)
![Create Order](screenshots/create-order.png)
```

---

## AI Usage Log
- Used Cursor AI/ChatGPT for project scaffolding, code generation, and debugging.
- Issues encountered: Serialization recursion, port conflicts, test failures.
- Resolutions: Added Jackson annotations, changed server port, fixed test data.
- All major steps and fixes were assisted by AI tools.
