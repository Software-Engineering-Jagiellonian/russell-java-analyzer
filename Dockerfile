FROM adoptopenjdk/openjdk11:x86_64-alpine-jdk-11.0.9.1_1
FROM adoptopenjdk/openjdk11:x86_64-alpine-jdk-11.0.9.1_1-slim
COPY --from=0 /opt/java/openjdk/bin /opt/java/openjdk/bin
RUN apk add --no-cache bash

ADD "build/libs/frege-java-analyzer-0.0.1-SNAPSHOT.jar" "/app"
ADD "run.sh" "/"
RUN chmod +x "/run.sh"

ENTRYPOINT ["/run.sh"]