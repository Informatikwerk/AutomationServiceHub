FROM openjdk:8-jre-alpine

RUN apk update
RUN apk add curl
RUN curl -sL https://deb.nodesource.com/setup_8.x
RUN apk add nodejs
RUN node -v

EXPOSE 8000
CMD ["npm", "start"]
