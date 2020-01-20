FROM maven:3.6.1-jdk-12 AS MAVEN_TOOL_CHAIN
COPY pom.xml /tmp/
COPY src /tmp/src/
WORKDIR /tmp/

RUN mvn clean -Dmaven.test.skip=true package -f pom.xml

FROM adoptopenjdk/openjdk12
COPY --from=MAVEN_TOOL_CHAIN /tmp/target/*.jar app.jar
# Make sure to provide environment variables `BOT_TOKEN`, `API_KEY` and `WORKSPACE_ID`
ENTRYPOINT exec java -jar /app.jar $BOT_TOKEN $API_KEY $WORKSPACE_ID