# per https://cloud.google.com/community/tutorials/kotlin-springboot-container-engine
# alpine buids are much smaller than other images, not available for anything after jdk8 though
FROM openjdk:8-alpine
MAINTAINER meshtastic.org
VOLUME /tmp
RUN mkdir /work
COPY ./build/libs/backend-1.0-SNAPSHOT.jar /work/app.jar
WORKDIR /work
# RUN /work/gradlew build
# RUN mv /work/build/libs/*.jar /work/app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/work/app.jar"]
