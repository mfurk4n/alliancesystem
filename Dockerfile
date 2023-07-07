FROM openjdk:17
ARG JAR_FILE=target/*.jar
EXPOSE 8080
COPY ${JAR_FILE} alliancesystem.jar
ENTRYPOINT ["java","-jar","/alliancesystem.jar"]