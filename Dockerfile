# Docker 이미지의 베이스로 사용할 이미지를 지정합니다. OpenJDK 17을 사용합니다.
FROM openjdk:17-alpine

# 작업 디렉토리를 설정합니다.
WORKDIR /app

# 호스트의 JAR 파일을 컨테이너의 /app 디렉토리로 복사합니다.
COPY build/libs/ph-cafe-0.0.1-SNAPSHOT.jar app.jar

# 컨테이너 내에서 8080 포트를 열어줍니다.
EXPOSE 8080

# Java 실행 경로를 지정합니다.
ENV PATH="/opt/java/openjdk/bin:${PATH}"

# 애플리케이션 실행 명령을 지정합니다.
CMD ["java", "-Dspring.profiles.active=docker", "-jar", "app.jar"]