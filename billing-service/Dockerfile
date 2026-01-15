# ===== STAGE 1: BUILD =====
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /build

# Copy pom.xml to cache dependencies
COPY pom.xml .

# Download dependencies (faster rebuilds)
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build the Spring Boot JAR
RUN mvn clean package -DskipTests

# ===== STAGE 2: RUNTIME =====
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /build/target/*.jar app.jar

# Expose Spring Boot port
EXPOSE 4001
EXPOSE 9001


# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
