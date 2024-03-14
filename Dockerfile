# Stage 1: Build the application
FROM maven:latest as builder
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

# Copie le reste du projet
COPY src src

# Construit le package sans exécuter les tests pour accélérer le processus
RUN mvn package -DskipTests

# Stage 2: Setup the application image
FROM openjdk:21
WORKDIR /app
# Utilisez une variable d'environnement pour spécifier le nom du fichier JAR
ARG APP_FILE=cast_games_api-0.0.1-SNAPSHOT.jar
COPY --from=builder /app/target/${APP_FILE} /app/myapp.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/myapp.jar"]
