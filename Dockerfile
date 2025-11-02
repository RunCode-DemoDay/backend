# ==== 1) Build stage: Gradle로 애플리케이션 빌드 ====
FROM gradle:8.10.2-jdk17 AS build
WORKDIR /home/gradle/src

# 의존성 캐시 최적화: 먼저 빌드 스크립트만 복사해서 dependency만 받아둠
COPY build.gradle settings.gradle gradle.properties* ./
COPY gradle ./gradle
RUN gradle --no-daemon dependencies || true

# 실제 소스 복사 후 빌드 (테스트는 스킵)
COPY . .
RUN gradle --no-daemon bootJar -x test

# ==== 2) Runtime stage: 슬림 JRE로 실행 ====
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# 빌드 산출물 복사 (jar 파일명 상관없이 *.jar로 대응)
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar

# 헬스체크(옵션): 8080 /actuator/health 기준
# 필요한 경우 Actuator 의존성과 엔드포인트 설정이 있어야 합니다.
# HEALTHCHECK --interval=30s --timeout=3s CMD wget -qO- http://localhost:8080/actuator/health || exit 1

# JVM 옵션 및 Spring 옵션 전달 가능
ENV JAVA_OPTS=""
ENV SPRING_OPTS=""

EXPOSE 8080
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar $SPRING_OPTS"]
