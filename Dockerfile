# Step 1: Build the app using Maven
FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Step 2: Run the built JAR with a lightweight JDK image
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

# Expose the app port
EXPOSE 8080

# Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]
