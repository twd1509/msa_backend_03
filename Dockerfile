FROM --platform=linux/amd64/v3 openjdk:17	
ARG JAR_FILE=./target/aianlaysis-0.0.1-SNAPSHOT.jar 	
COPY ${JAR_FILE} aianlaysis-0.0.1-SNAPSHOT.jar 	
ENTRYPOINT ["java","-jar","/aianlaysis-0.0.1-SNAPSHOT.jar"]