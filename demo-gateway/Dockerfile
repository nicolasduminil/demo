FROM openjdk:11-slim
LABEL maintainer="Nicolas DUMINIL <nduminil-ext@agirc-arrco.fr>"
COPY target/demo-gateway.jar demo-gateway.jar
ENTRYPOINT ["java","-jar","demo-gateway.jar"]