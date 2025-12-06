FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

# Copy Maven descriptor and fetch dependencies first for better layer caching
COPY backend/pom.xml .
COPY backend/.mvn .mvn
RUN mvn -B -q -DskipTests dependency:go-offline

# Copy application sources
COPY backend/src src

# Build the application without running tests (tests should run in CI)
RUN mvn -B -DskipTests package

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/target/content-ingestion-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
