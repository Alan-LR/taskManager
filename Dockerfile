FROM eclipse-temurin:17
LABEL maintainer="alanlopes.r@gmail.com"
WORKDIR /app
COPY target/taskManager-0.0.1-SNAPSHOT.jar /app/exec-taskManager.jar
ENTRYPOINT ["java", "-jar", "exec-taskManager.jar"]

