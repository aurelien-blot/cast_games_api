# Stage 1: Build the application
FROM maven:latest as builder
WORKDIR /app
COPY . .
RUN mvn clean package

# Stage 2: Setup the application image
FROM openjdk:21
WORKDIR /app
COPY --from=builder /app/target/${APP_FILE} /app/myapp.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/myapp.jar"]
