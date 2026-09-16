# Étape 1 : Construction de l'application (Build)
FROM eclipse-temurin:26-jdk AS build
WORKDIR /app

# Copier les fichiers Maven et le code source
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
COPY src src

# Rendre le Maven wrapper exécutable (au cas où) et lancer le package en skippant les tests
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Étape 2 : Exécution de l'application (Runtime)
FROM eclipse-temurin:26-jre
WORKDIR /app

# Copier le JAR généré depuis l'étape de build précédente
COPY --from=build /app/target/*.jar app.jar

# Exposer le port par défaut de Spring Boot
EXPOSE 8080

# Lancer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]