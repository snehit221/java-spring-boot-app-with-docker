# User Management API (Spring Boot + Docker)

* *URL*: <https://sne-tutorial5.onrender.com/>
* *Git URL*: <https://git.cs.dal.ca/roda/csci-5709-tutorials/-/tree/main/Tutorial5>


This project is a **RESTful API** for managing users, built with **Spring Boot** and **MongoDB**. It provides endpoints to create, read, and update user data. The application is packaged with **Docker**, allowing it to be easily deployed as a containerized service.

## Features

* **User CRUD Operations**: Retrieve all users or a specific user by ID, add new users, and update existing users.
* **MongoDB Persistence**: Uses MongoDB as the database for storing user information, benefiting from flexible JSON-like document storage and easy data retrieval.
* **Docker Containerization**: Docker support is included for running the app in an isolated container, ensuring consistent behavior across different environments.

## Tech Stack

* **Java 17** – Core language used for development.
* **Spring Boot** – Framework for rapid application development in Java. Simplifies configuration and provides an embedded server to run the app.
* **Spring Data MongoDB** – Library to integrate Spring Boot with MongoDB, providing repository interfaces for easy data access.
* **MongoDB** – NoSQL database for storing user data, chosen for its flexibility and ease of data storage/retrieval.
* **Docker** – Containerization platform to package the application and its environment into a portable container.

## Prerequisites

* **Java JDK 17+** – Ensure Java is installed and `JAVA_HOME` is configured.
* **Maven** – Install Maven (or use the Maven Wrapper included in this project).
* **MongoDB** – Install and run MongoDB (default port 27017) on your local machine, or have access to a MongoDB instance.
* **Docker** – Install Docker (needed only if you plan to run the application in a Docker container).

## Running Locally

1. **Clone the repository**:

   ```bash
   git clone https://github.com/snehit221/java-spring-boot-app-with-docker.git
   cd java-spring-boot-app-with-docker
   ```
2. **Configure MongoDB**: Ensure MongoDB is running on `localhost:27017`. By default, the app will connect to a local MongoDB instance. You can check (or adjust) the connection in `src/main/resources/application.properties` (for example, setting `spring.data.mongodb.uri=mongodb://localhost:27017/<your-db-name>`).
3. **Build the project**: Use Maven to compile and package the application:

   ```bash
   mvn clean package
   ```

   This will produce a jar file in the `target/` directory.
4. **Run the application**:

   * **Using Maven**: Run the Spring Boot app with Maven's Spring Boot plugin:

     ```bash
     mvn spring-boot:run
     ```

     *OR*
   * **Using the JAR**: Run the packaged jar file:

     ```bash
     java -jar target/java-spring-boot-app-with-docker-0.0.1-SNAPSHOT.jar
     ```

     *(Use the actual jar name if it differs.)*
5. **Verify**: Once the application starts, it listens on port 8080. Visit `http://localhost:8080/users` in your browser or use a tool like curl/Postman to call the endpoints. You should get a response (e.g., an empty list `[]` if no users exist yet).

## Running with Docker

Ensure Docker is installed and running on your system.

1. **Build the Docker image**:

   ```bash
   # First, package the application (if not done already)
   mvn clean package
   # Then build the Docker image
   docker build -t user-management-api .
   ```

   This uses the provided `Dockerfile` to create an image named **`user-management-api`**.
2. **Run the Docker container**:

   ```bash
   docker run -d -p 8080:8080 --name user-api-container user-management-api
   ```

   This starts the container in detached mode, mapping port 8080 of the container to port 8080 on your host.
3. **MongoDB connectivity**: The containerized application needs to reach a MongoDB instance:

   * If you have MongoDB running on the host machine, the container may need special configuration to connect. On Docker Desktop (Windows/Mac), the host MongoDB can be reached via the hostname `host.docker.internal`. You can modify the MongoDB URI in the application properties (or via an environment variable) to use this host before building the image (e.g., `mongodb://host.docker.internal:27017/<your-db-name>`).
   * Alternatively, run a MongoDB container and allow the app to connect to it. For example:

     ```bash
     docker run -d --name mongodb -p 27017:27017 mongo:latest
     ```

     This command runs a MongoDB container accessible on port 27017 on your host. Since we published the port, the application (even inside its container) can still use `mongodb://localhost:27017` to connect (assuming the containers share the same network or you're on Docker Desktop).
   * For a more robust multi-container setup, consider using **Docker Compose** (see *Future Improvements*).
4. **Verify**: When the app container is running, test the API as you would locally (e.g., `GET http://localhost:8080/users`). The API endpoints should respond the same way as when running locally.

## API Endpoints

The following endpoints are available in this User Management API:

| Method | Endpoint      | Description                    |
| ------ | ------------- | ------------------------------ |
| GET    | `/users`      | Get a list of all users.       |
| GET    | `/users/{id}` | Get a single user by its ID.   |
| POST   | `/users`      | Add a new user.                |
| PUT    | `/users/{id}` | Update an existing user by ID. |

* **GET /users** – Returns an array of all user objects in the system.
* **GET /users/{id}** – Returns the user object with the given ID (or 404 Not Found if no such user exists).
* **POST /users** – Creates a new user from the JSON request body and returns the created user (with a generated unique ID).
* **PUT /users/{id}** – Updates the existing user with the given ID using the JSON body data. Returns the updated user, or 404 if the user ID is not found.

*(Note: There is currently no DELETE endpoint in this project.)*

## Sample Request Body

When adding a new user via **POST /users** (or updating via **PUT /users/{id}**), the API expects a JSON payload. For example, to create a new user:

```json
{
  "name": "Alice Smith",
  "email": "alice.smith@example.com"
}
```

In the response, the created/updated user will include an `id` field (assigned by MongoDB) along with the data you provided.

## Project Structure

```
java-spring-boot-app-with-docker/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── *your/base/package* /
│   │   │       ├── controller/
│   │   │       │   └── UserController.java   # REST controller defining user endpoints.
│   │   │       ├── model/
│   │   │       │   └── User.java            # Model class representing a User document.
│   │   │       ├── repository/
│   │   │       │   └── UserRepository.java  # Interface for MongoDB CRUD operations.
│   │   │       └── Application.java         # Main Spring Boot application class.
│   │   └── resources/
│   │       └── application.properties       # Configuration (MongoDB URI, etc.).
│   └── test/                                # (Test files, if any)
├── Dockerfile                               # Docker configuration to containerize the app.
├── pom.xml                                  # Maven dependencies and build configuration.
└── README.md                                # Project documentation (this file).
```

* **UserController.java** – Contains the REST endpoints (`GET /users`, `GET /users/{id}`, `POST /users`, `PUT /users/{id}`) for user management.
* **User.java** – Entity/model class for users. Likely annotated with `@Document` (Spring Data MongoDB) to map to a MongoDB collection.
* **UserRepository.java** – Repository interface (extends `MongoRepository` or similar) providing CRUD operations on the User collection.
* **Application.java** – Main application entry point (with the `main` method to run the Spring Boot application).
* **application.properties** – Configuration properties for the application (e.g., database connection string, server port).
* **Dockerfile** – Defines the Docker image build steps (using a base Java image, copying the jar, and setting the startup command).
* **pom.xml** – Maven project file, listing dependencies like Spring Boot Starter Web, Spring Data MongoDB, and build plugins.

## Future Improvements

* **Validation & Error Handling**: Implement request validation (e.g., required fields for `User`) and more robust error responses (e.g., return proper HTTP status codes for invalid input).
* **DTOs and Service Layer**: Introduce a service layer and use DTOs (Data Transfer Objects) to decouple the API from internal data models and to handle business logic outside the controller.
* **Authentication & Security**: Integrate security (e.g., Spring Security with JWT) to protect the endpoints, so that only authorized clients can modify user data.
* **API Documentation**: Add documentation for the API endpoints (using Swagger/OpenAPI) to allow easy exploration and testing of the API.
* **Docker Compose**: Provide a `docker-compose.yml` to easily spin up the application and a MongoDB database together with one command.
* **Tests**: Add unit and integration tests for the controller and repository to ensure reliability and catch regressions early.


