# 1단계: 빌드 단계
FROM gradle:8.6-jdk17 AS build

# 작업 디렉토리 설정
WORKDIR /app

# Gradle Wrapper 및 설정 파일 복사
COPY ../gradlew .
COPY ../gradle gradle/
COPY ../settings.gradle.kts .
COPY ../build.gradle.kts .

# 모듈 빌드
RUN ./gradlew build -x test --stacktrace --no-daemon || return 0

# 해당 모듈의 소스 복사
COPY knowledge/ knowledge/
COPY core/ core/

# 필요한 모듈 빌드
RUN ./gradlew :knowledge:build -x test --stacktrace --no-daemon

# 2단계: 실행 단계
FROM openjdk:17-jdk-slim

# 작업 디렉토리 설정
WORKDIR /app

# 빌드 결과물 복사
COPY --from=build /app/knowledge/build/libs/*.jar app.jar

# 컨테이너 실행 시 애플리케이션 실행
ENV SPRING_PROFILE "dev"
ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILE}", "-jar", "app.jar"]
