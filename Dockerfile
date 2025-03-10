FROM openjdk:23-jdk-slim AS build

WORKDIR /app

COPY . .

RUN ./gradlew build -x test --no-daemon

FROM openjdk:23-jdk-slim

WORKDIR /app

COPY --from=build /app/build/libs/techrodeo-crm-bff-0.0.1-SNAPSHOT.jar techrodeo-crm-bff.jar

ENTRYPOINT ["java", "-jar", "techrodeo-crm-bff.jar"]

EXPOSE 8081
