#FROM openjdk:8-jdk-alpine
FROM openjdk:8-alpine


#aws
RUN set -eux; \
    apk add python3 ; \
    pip3 --no-cache-dir install -U awscli

#jq
RUN wget https://github.com/stedolan/jq/releases/download/jq-1.6/jq-linux64 && \
    mv jq-linux64 /usr/local/bin/jq && \
    chmod +x /usr/local/bin/jq


VOLUME /tmp
COPY target/*.jar app.jar
#ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]
ENTRYPOINT ["java", "-jar", "/app.jar"]

