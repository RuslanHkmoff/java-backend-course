FROM openjdk:latest

WORKDIR /app

COPY scrapper/target/scrapper.jar.jar /app

EXPOSE 8080
EXPOSE 8081

CMD ["java", "-jar", "scrapper.jar"]
