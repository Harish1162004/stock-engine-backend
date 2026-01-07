# Use Java 17 (recommended for Spring Boot)
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Build the project
RUN ./mvnw clean package -DskipTests

# Expose Render port
EXPOSE 8080

# Run the Spring Boot JAR
CMD ["java", "-jar", "target/*.jar"]
