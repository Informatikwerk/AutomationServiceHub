FROM openjdk:8-jre-alpine

RUN apk update
RUN wget -sL https://deb.nodesource.com/setup_8.x | -E bash -
RUN apk install nodejs
RUN node -v

EXPOSE 8000
CMD ["npm", "start"]