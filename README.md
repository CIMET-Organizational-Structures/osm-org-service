Sure! Here's a README for your OSM Organization Service:

---

# OSM Organization Service

A Spring Boot application that provides RESTful APIs to manage repositories and contributors with full HATEOAS implementation.

## Prerequisites

- Java 17+
- Maven 3.6+
- Docker and Docker Compose

## Building the Application

Clone the repository and build the application:

```bash
    git clone <repository-url>
    cd osm-organization-service
    mvn clean package
```

## Creating Docker Image

Build the Docker image using the provided Dockerfile:

```bash
  docker build -t osm-organization-service:latest .
```

Alternatively, if configured in your pom.xml:

```bash
  mvn spring-boot:build-image
```

## Running with Docker Compose

The docker-compose.yml file is already available in the project root:

```bash
    # Start the application and dependencies
    docker-compose up -d
    
    # View logs
    docker-compose logs -f
    
    # Stop the application
    docker-compose down
```

## API Endpoints

### Repositories
- `GET /api/v1/repos` - List all repositories
- `GET /api/v1/repos/{id}` - Get repository by ID
- `POST /api/v1/repos` - Create new repository
- `PUT /api/v1/repos/{id}` - Update repository
- `DELETE /api/v1/repos/{id}` - Delete repository

### Contributors
- `GET /api/v1/contributors` - List all contributors
- `GET /api/v1/contributors/{id}` - Get contributor by ID
- `POST /api/v1/contributors` - Create new contributor
- `PUT /api/v1/contributors/{id}` - Update contributor
- `DELETE /api/v1/contributors/{id}` - Delete contributor
