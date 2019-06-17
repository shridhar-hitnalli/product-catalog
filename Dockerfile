FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/catalog-0.0.1-SNAPSHOT.jar catalog-api.jar
EXPOSE 9999
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/catalog-api.jar"]