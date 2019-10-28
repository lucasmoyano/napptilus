#pull base image
FROM openjdk:8-jdk-alpine

#maintainer 
MAINTAINER dstar55@yahoo.com

#expose port 8080
EXPOSE 8080

#default command
CMD java -jar /data/willywonka-0.1.0.jar

#copy hello world to docker image
ADD ./data/willywonka-0.1.0.jar /data/willywonka-0.1.0.jar