FROM openjdk:latest

WORKDIR /app

COPY bot/target/bot.jar /app

EXPOSE 8090
EXPOSE 8091

CMD ["java", "-jar", "bot.jar"]
