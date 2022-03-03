FROM ubuntu

RUN mkdir /problems && chmod go+w /problems
RUN mkdir /execute && chmod go+w /execute

RUN apt update && apt upgrade -y
RUN apt install openjdk-11-jdk -y
RUN apt install build-essential -y

ARG JAR_FILE=build/libs/judge-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java" ,"-jar","/app.jar"]