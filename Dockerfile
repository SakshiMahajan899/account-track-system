FROM openjdk:17-jdk

WORKDIR /app

ADD target/bank-application-0.0.1-SNAPSHOT.jar /app/bank-application.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "bank-application.jar"]
