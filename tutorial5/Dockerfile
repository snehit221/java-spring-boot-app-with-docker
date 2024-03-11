FROM --platform=linux/amd64 openjdk:17-slim AS builder

WORKDIR /app

COPY pom.xml ./
RUN mvn clean package -DskipTests

FROM openjdk:17-slim AS runner

WORKDIR /app

COPY /target/tutorial5-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENV PORT 8080

ENTRYPOINT ["java", "-jar", "app.jar"]