# ---- builder ----
FROM gradle:8.9-jdk17 AS builder
WORKDIR /app
# 캐시 최적화: 설정/의존 먼저 복사
COPY build.gradle settings.gradle gradlew gradle/ ./
RUN chmod +x gradlew
# 소스 복사
COPY . .
# 테스트 스킵 빌드
RUN ./gradlew clean bootJar -x test

# ---- runtime ----
FROM eclipse-temurin:17-jre
WORKDIR /app
# 빌드 산출물 복사
COPY --from=builder /app/build/libs/*.jar /app/app.jar

# Spring Boot는 8080에서 리스닝 (Nginx가 프록시)
EXPOSE 8080
ENV SPRING_PROFILES_ACTIVE=prod
# 필요하다면 JVM 옵션
ENV JAVA_OPTS="-Xms256m -Xmx512m"

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar /app/app.jar"]
