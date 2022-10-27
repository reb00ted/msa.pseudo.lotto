FROM openjdk:17-alpine
#RUN ["./gradlew", "build"]
COPY ./build/libs/user-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "/app.jar"]
# datasource 설정 변경해야함
# image 빌드 시 gradlew build 를 이용해서 jar 파일 생성후 해야함



#FROM openjdk:8-jdk-alpine
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java", "-jar", "/app.jar"]