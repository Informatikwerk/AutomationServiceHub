FROM openjdk:8-jre-alpine

RUN apt-get update
RUN curl -sL https://deb.nodesource.com/setup_8.x | -E bash -
RUN apt-get install nodejs
RUN node -v

EXPOSE 8000
CMD ["npm", "start"]