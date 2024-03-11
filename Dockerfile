# Utiliser l'image de base officielle OpenJDK
FROM openjdk:21

# Optionnel : définir une variable d'environnement pour stocker le nom du fichier JAR
ENV APP_FILE=cast_games_api-0.0.1-SNAPSHOT.jar

# Créer un répertoire dans le conteneur pour stocker l'application
WORKDIR /app

# Copier le fichier JAR exécutable de votre application Spring Boot dans le répertoire /app
COPY target/${APP_FILE} /app/myapp.jar

# Exposer le port sur lequel votre application écoute (par défaut, Spring Boot utilise le port 8080)
EXPOSE 8080

# Commande pour exécuter l'application
ENTRYPOINT ["java","-jar","/app/myapp.jar"]
