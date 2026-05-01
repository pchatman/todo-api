# todo-api

A simple REST API for managing to-do tasks, built with Java Spring Boot.

---

## Tech Stack

- Java 17
- Spring Boot 3
- Spring Data JPA
- H2 In-Memory Database
- Lombok
- Maven

---

## Project Details

| Field        | Value              |
|--------------|--------------------|
| Group        | `com.example`      |
| Artifact     | `todo-api`         |
| Package      | `com.example.todo` |

---

## Getting Started

```bash
git clone https://github.com/your-username/todo-api.git
cd todo-api
./mvnw spring-boot:run
```

API runs at: `http://localhost:8080`

---

## API Endpoints

| Method   | Endpoint                           | Description         |
|----------|------------------------------------|---------------------|
| `GET`    | `/api/todos`                       | Get all todos       |
| `GET`    | `/api/todos/{id}`                  | Get todo by ID      |
| `GET`    | `/api/todos/filter?completed=true` | Filter by status    |
| `POST`   | `/api/todos`                       | Create a todo       |
| `PUT`    | `/api/todos/{id}`                  | Update a todo       |
| `DELETE` | `/api/todos/{id}`                  | Delete a todo       |

---

## License

Open source under the [MIT License](LICENSE).
