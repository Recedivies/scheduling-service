FROM gradle:jdk17-alpine
ARG PRODUCTION
ARG JDBC_DATABASE_PASSWORD
ARG JDBC_DATABASE_URL
ARG JDBC_DATABASE_USERNAME

ARG REDIS_HOST
ARG REDIS_PORT
ARG REDIS_USERNAME
ARG REDIS_PASSWORD

ARG RABBITMQ_HOST
ARG RABBITMQ_PORT
ARG RABBITMQ_USER
ARG RABBITMQ_PASSWORD

ENV PRODUCTION ${PRODUCTION}
ENV JDBC_DATABASE_PASSWORD ${JDBC_DATABASE_PASSWORD}
ENV JDBC_DATABASE_URL ${JDBC_DATABASE_URL}
ENV JDBC_DATABASE_USERNAME ${JDBC_DATABASE_USERNAME}

ENV REDIS_HOST ${REDIS_HOST}
ENV REDIS_PORT ${REDIS_PORT}
ENV REDIS_USERNAME ${REDIS_USERNAME}
ENV REDIS_PASSWORD ${REDIS_PASSWORD}

ENV RABBITMQ_HOST ${RABBITMQ_HOST}
ENV RABBITMQ_PORT ${RABBITMQ_PORT}
ENV RABBITMQ_USER ${RABBITMQ_USER}
ENV RABBITMQ_PASSWORD ${RABBITMQ_PASSWORD}

WORKDIR /app
COPY ./build/libs/scheduling_service-0.0.1-SNAPSHOT.jar /app
EXPOSE 8080
CMD ["java","-jar","scheduling_service-0.0.1-SNAPSHOT.jar"]