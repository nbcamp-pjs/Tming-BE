FROM public.ecr.aws/amazoncorretto/amazoncorretto:17 AS builder
ARG JAR_FILE=build/libs/Tming-BE-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} tming.jar
ENTRYPOINT ["java", "-Duser.timezone=Asia/Seoul", "-jar", "-Dspring.profiles.active=prod", "/tming.jar"]
