# Step 1: Use the official Maven image with OpenJDK 17 to build the project
FROM maven:3.8.6-openjdk-17-slim AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml file to download dependencies
COPY pom.xml ./

# Fetch dependencies to improve subsequent build times unless pom.xml changes
RUN mvn dependency:go-offline -B

# Copy the project source
COPY src src

# Package the application
RUN mvn clean package -DskipTests

# Step 2: Use OpenJDK 17 to run the application
FROM openjdk:17-slim

# Set deployment directory
WORKDIR /app

# Copy only the artifact from the build stage and discard the rest
COPY --from=build /app/target/*.jar app.jar

# Command to execute the application
CMD ["java", "-jar", "app.jar"]

# Optional: if your application uses a port (e.g., 8080), expose it:
EXPOSE 8080
