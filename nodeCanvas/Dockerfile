FROM node:7

#Create app directory

WORKDIR /usr/src/app

COPY package*.json ./

RUN npm install

copy . .

expose 3000

CMD ["npm","start"]