FROM openjdk:12-alpine

COPY target/*jar /demo.jar

CMD ["java", "-jar", "/demo.jar"]