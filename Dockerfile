FROM openjdk:8-jdk-alpine
CMD java -jar /app/catalog-0.0.1-SNAPSHOT.jar
EXPOSE 9999
ADD ./app/catalog-0.0.1-SNAPSHOT.jar /app/catalog-0.0.1-SNAPSHOT.jar