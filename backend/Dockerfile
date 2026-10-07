# ===== Etapa 1: compilación =====
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /build

# Primero solo el pom: así las dependencias quedan en caché entre builds
COPY pom.xml .
RUN mvn -B -q dependency:go-offline

COPY src ./src
RUN mvn -B -q clean package -DskipTests

# ===== Etapa 2: imagen final =====
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Usuario sin privilegios
RUN groupadd -r learnhub && useradd -r -g learnhub learnhub
COPY --from=build /build/target/*.jar app.jar
USER learnhub

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
