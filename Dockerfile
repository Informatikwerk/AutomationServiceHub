FROM openjdk:8-jre-alpine

RUN apt update
RUN curl -sL https://deb.nodesource.com/setup_8.x | -E bash -
RUN apt install nodejs
RUN node -v

EXPOSE 8000
CMD ["npm", "start"]