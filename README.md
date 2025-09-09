# Spring Boot PostgreSQL CRUD Example

## Project Overview

**Tech Stack:**  
- Spring Boot 3.x  
- Java 17  
- PostgreSQL  
- Spring Data JPA  
- Maven  
- Podman (containerization)  
- H2 (for test isolation)  
- JUnit 5, MockMvc, curl, jq

**Key Highlights:**  
- Clean three-layer architecture (Controller, Service, Repository)
- Uses Java record for DTOs
- Containerized with Podman for both app and database
- Includes automated integration tests and runtime curl test script
- Example output and Podman usage documented in README
- Pay attention to:  
  - Correct DB host in container networking  
  - Test isolation with H2  
  - Use of jq for readable API output  
  - Reference links and Copilot GPT-4.1 assistance




## Runtime API Test Script

To test the CRUD API endpoints at runtime, use the provided shell script:

```sh
sh test-crud-api.sh
```


This script will:
- Create a product
- List all products
- Get a product by ID
- Update a product by ID
- Delete a product by ID
- List all products after deletion

The script prints human-readable, pretty-formatted JSON output for each step. Make sure `jq` is installed for best output formatting.

## Podman Distribution Guideline

To run the application and database with Podman:

```sh
# Create a Podman network
podman network create spring-crud-net

# Start PostgreSQL container
podman run --name postgres-crud --network spring-crud-net \
  -e POSTGRES_DB=productdb \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=yourpassword \
  -p 5432:5432 -d docker.io/library/postgres:16

# Build the Spring Boot app image
podman build -t localhost/spring-boot-postgresql-crud:latest .

# Run the Spring Boot app container
podman run --name spring-boot-crud-app --network spring-crud-net \
  -p 8080:8080 -d localhost/spring-boot-postgresql-crud:latest
```

### Sample Output

```
Creating a product...
{
  "id": 2,
  "name": "Laptop",
  "description": "A high-performance laptop.",
  "price": 1200.0
}
Getting all products...
[
  {
    "id": 2,
    "name": "Laptop",
    "description": "A high-performance laptop.",
    "price": 1200.0
  }
]
Getting product by ID (1)...
Updating product by ID (1)...
Deleting product by ID (1)...
Getting all products after deletion...
[
  {
    "id": 2,
    "name": "Laptop",
    "description": "A high-performance laptop.",
    "price": 1200.0
  }
]
```

---

**References:**

- [Spring Boot CRUD Example with PostgreSQL (Ramesh Fadatare)](https://www.rameshfadatare.com/spring-boot-tutorial/spring-boot-crud-example-with-postgresql/)
- [Spring Boot CRUD Example with PostgreSQL (Medium)](https://rameshfadatare.medium.com/spring-boot-crud-example-with-postgresql-926c87f0129a)

*This example was assisted by Copilot GPT-4.1.*
