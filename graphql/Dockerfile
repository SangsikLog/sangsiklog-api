FROM gradle:8.6-jdk17 AS build

WORKDIR /app

COPY ../gradlew .
COPY ../gradle gradle/
COPY ../settings.gradle.kts .
COPY ../build.gradle.kts .

RUN ./gradlew build -x test --stacktrace --no-daemon || return 0

COPY graphql/ graphql/
COPY core/ core/
COPY grpc-common/ grpc-common/

RUN ./gradlew -Pprotoc_platform=linux-x86_64 :graphql:build -x test --stacktrace --no-daemon

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/graphql/build/libs/*.jar app.jar

ENV SPRING_PROFILE "real"
ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILE}", "-jar", "app.jar"]
