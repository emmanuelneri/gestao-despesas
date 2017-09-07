FROM node:6.11.2

WORKDIR /opt/despesas-web

ADD dist .

RUN npm install
EXPOSE 3000

CMD ["node", "server.js"]