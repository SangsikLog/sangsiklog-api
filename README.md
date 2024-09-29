# Sangsiklog API
Sangsiklog API는 Sangsiklog 웹 애플리케이션의 백엔드 서버로, 다양한 모듈로 구성되어 있으며 gRPC 및 GraphQL을 사용하여 클라이언트와 통신합니다.

## 프로젝트 구조

- **언어:** Kotlin
- **주요 모듈:**
    - **auth:** 인증 및 권한 관리 - RestAPI 컴포넌트
    - **content:** 콘텐츠 관리 - Grpc 컴포넌트
    - **graphql:** GraphQL API 처리
    - **gateway:** API Gateway 역할
    - **discovery:** 서비스 디스커버리
    - **grpc-common:** gRPC 통신 지원
    - **core:** 공통 모듈
- **빌드 시스템:** Gradle
- **infrastructure:** 개발환경

## 아키텍처

![제목 없는 다이어그램-페이지-8 drawio (1)](https://github.com/user-attachments/assets/af4fb539-af51-493c-b8bb-6b76f380c76e)

## 주요 기능

- 인증 및 권한 관리
- 지식 및 카테고리 CRUD
- gRPC 및 GraphQL을 통한 데이터 전송
- 서비스 디스커버리 및 게이트웨이 설정

## 설치 및 실행

### 요구 사항
- JDK 17 이상
- Gradle 8.x
- Docker

### 로컬에서 실행

```bash
# 코드 복사
git clone https://github.com/SangsikLog/sangsiklog-api.git
```

```bash
# 개발환경 세팅
cd sangsiklog-api/infrastructure/local
docker compose up
```

```bash
# 각 애플리케이션 실행
git clone https://github.com/SangsikLog/sangsiklog-api.git
cd sangsiklog-api
./gradlew :discovery:bootRun --args='--spring.profiles.active=local'
./gradlew :auth:bootRun --args='--spring.profiles.active=local'
./gradlew :content:bootRun --args='--spring.profiles.active=local'
./gradlew :graphql:bootRun --args='--spring.profiles.active=local'
./gradlew :gateway:bootRun --args='--spring.profiles.active=local'
```

### API 문서
GraphQL 엔드포인트: /graphql
gRPC 서비스 파일: grpc-common/src/main/proto에 위치

### 환경 설정
필요하다면 application.yml에서 설정을 조정하세요:

```yaml
spring:
  datasource:
    url: ${DB_URL}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
```

## 기여
기여를 환영합니다! 풀 리퀘스트 또는 이슈를 제출해주세요.

## 라이선스
MIT License.

