# DevOps Java Sample API

This repository contains a simple Spring Boot application used for demonstrating a basic REST API, reporting features, and integration with typical DevOps tooling. It's a sample project intended for learning purposes and includes some intentionally flawed code to illustrate static analysis (e.g. SonarQube) findings.

##  Features

- REST endpoints for managing dummy tasks
- In-memory storage (no database required)
- Simple report generation and metrics
- System information endpoint
- Includes unit tests and build scripts

##  Prerequisites

Before you begin, ensure you have the following installed:

- Java 17+ (JDK)
- Maven 3.6+ (or use the provided `mvnw` wrapper)
- Git (for cloning)

> macOS users can install via Homebrew: `brew install openjdk@17 maven git`

##  Building the Project

Use Maven to compile and package the application. From the project root:

```bash
# clean and build without running tests (faster)
./mvnw clean package -DskipTests

# or with system maven installation
mvn clean package -DskipTests
```

The resulting artifact will be placed in `target/devops-java-app-0.0.1-SNAPSHOT.jar`.

## ▶ Running the Application

Start the Spring Boot jar:

```bash
java -jar target/devops-java-app-0.0.1-SNAPSHOT.jar
```

The server listens on port **8080** by default. You can verify it's running by visiting [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health) if Spring Actuator is enabled.

##  API Endpoints

| Method | Path           | Description                       |
|--------|----------------|-----------------------------------|
| GET    | `/api/tasks`   | List all tasks                    |
| GET    | `/api/system-info` | Get OS, Java version, owner    |
| GET    | `/api/report`  | Generate a plain‑text task report |
| GET    | `/api/metrics` | Return metrics map (contains hardcoded "credentials") |

Example using `curl`:

```bash
curl http://localhost:8080/api/tasks
```

##  Running Tests

A basic unit test exists under `src/test/java`. Execute tests with:

```bash
./mvnw test
```

##  Notes & Known Issues

- The `ReportGenerator` class contains hardcoded credentials and several code smells intentionally added for demonstration purposes (e.g. null dereference, string concatenation in loops).
- This project is not production‑ready and is intended only for educational use.

##  Contributing

Feel free to fork the repository and submit pull requests. Addressing the SonarQube issues or adding new features would be appreciated.

##  License

This sample is provided under the MIT License. See [LICENSE](LICENSE) for details.
