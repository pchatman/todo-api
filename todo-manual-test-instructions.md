
## 1. Start the Application

In IntelliJ, open `TodoApiApplication.java` and click the **green play button** ▶

Look for this in the console to confirm it's running:
```
Started TodoApiApplication in 2.345 seconds
```

---

## 2. Open Postman

Download Postman at `https://www.postman.com/downloads` if you don't have it.

Set the base URL to:
```
http://localhost:8080/api/todos
```

---

## 3. Create a Todo — `POST`

- Method: `POST`
- URL: `http://localhost:8080/api/todos`
- Click **Body → raw → JSON**
- Paste:
```json
{
  "title": "Buy groceries",
  "description": "Milk, eggs, bread",
  "completed": false
}
```
- Click **Send**
- You should get back `201 Created`:
```json
{
  "id": 1,
  "title": "Buy groceries",
  "description": "Milk, eggs, bread",
  "completed": false,
  "createdAt": "2024-01-01T10:00:00"
}
```

---

## 4. Get All Todos — `GET`

- Method: `GET`
- URL: `http://localhost:8080/api/todos`
- Click **Send**
- You should get back `200 OK`:
```json
[
  {
    "id": 1,
    "title": "Buy groceries",
    "description": "Milk, eggs, bread",
    "completed": false,
    "createdAt": "2024-01-01T10:00:00"
  }
]
```

---

## 5. Get a Single Todo — `GET`

- Method: `GET`
- URL: `http://localhost:8080/api/todos/1`
- Click **Send**
- You should get back `200 OK` with just that todo

---

## 6. Update a Todo — `PUT`

- Method: `PUT`
- URL: `http://localhost:8080/api/todos/1`
- Click **Body → raw → JSON**
- Paste:
```json
{
  "title": "Buy groceries",
  "description": "Milk, eggs, bread, butter",
  "completed": true
}
```
- Click **Send**
- You should get back `200 OK` with the updated todo

---

## 7. Filter by Status — `GET`

- Method: `GET`
- URL: `http://localhost:8080/api/todos/filter?completed=true`
- Click **Send**
- Returns only completed todos

---

## 8. Delete a Todo — `DELETE`

- Method: `DELETE`
- URL: `http://localhost:8080/api/todos/1`
- Click **Send**
- You should get back `204 No Content` — todo is deleted

---

## Quick Reference

| Step | Method | URL |
|---|---|---|
| Create | `POST` | `/api/todos` |
| Get all | `GET` | `/api/todos` |
| Get one | `GET` | `/api/todos/{id}` |
| Filter | `GET` | `/api/todos/filter?completed=true` |
| Update | `PUT` | `/api/todos/{id}` |
| Delete | `DELETE` | `/api/todos/{id}` |

---

> You can also view the H2 database in your browser at `http://localhost:8080/h2-console` while the app is 
> running to see your data in real time.

> Option: Use SQL editor in H2 database: 
```sql
-- View all todos
SELECT * FROM TODO; 

-- View only completed todos
SELECT * FROM TODO WHERE COMPLETED = TRUE;

-- View only incomplete todos
SELECT * FROM TODO WHERE COMPLETED = FALSE;

-- Count all todos
SELECT COUNT(*) FROM TODO;
```

> httpRequests run within the program `todo-api.http` folder.
> 
> manual http request with postman instructions [todo-manual-test-instructions](todo-manual-test-instructions.md)