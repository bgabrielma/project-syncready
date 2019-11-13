FROM node:7.7.2-alpine

WORKDIR /usr/app


COPY ../backend/package.json .

RUN npm install --quiet
RUN npm start

COPY ../ .