# Step 1: Start with the OpenJDK 17 base image
FROM openjdk:17-slim AS build

# Set the working directory inside the container
WORKDIR /app

# Install Maven
RUN apt-get update && \
    apt-get install -y maven

# Copy the project files into the container
COPY pom.xml ./

# Fetch dependencies to improve subsequent build times unless pom.xml changes
RUN mvn dependency:go-offline

# Copy the project source code
COPY src src

# Build the project and package the application, skipping tests to speed up the build
RUN mvn clean package -DskipTests

# Step 2: Use the same OpenJDK image to run the application
FROM openjdk:17-slim

# Set the deployment directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Define the command to run the application
CMD ["java", "-jar", "app.jar"]

# Optional: expose the port your application uses
EXPOSE 8080
