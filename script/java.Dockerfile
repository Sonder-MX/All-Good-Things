FROM openjdk:17-jdk-alpine

RUN apk add --no-cache fontconfig ttf-dejavu

WORKDIR /server

COPY ./application.yml /server/application.yml
COPY ./scrbkend-release.jar /server/scrbkend-release.jar

EXPOSE 8080

CMD ["java", "-jar", "/server/scrbkend-release.jar"]