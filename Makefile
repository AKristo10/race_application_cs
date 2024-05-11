# CI/CD Commands

# Command to build Docker images for the application
docker-build:
    docker-compose build

# Command to run tests
test:
    docker-compose run --rm app mvn test


# Development Team Commands

# Command to start the development environment (Docker containers)
dev-start:
    docker-compose up -d

# Command to stop the development environment
dev-stop:
    docker-compose down

# Command to run the application locally
run:
    docker-compose up -d app

# Command to build the Java application
build-java:
    docker-compose run --rm app mvn package

# Command to build Docker images for the application
build-docker:
    docker-compose build app

# Command to clean up the development environment
clean:
    docker-compose down --volumes --remove-orphans
