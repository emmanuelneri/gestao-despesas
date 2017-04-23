var http = require('http');
var yargs = require('yargs').argv;
var app = require('./config/express')();

var port = yargs.port || app.get('port');

http.createServer(app).listen(port, function() {
	console.log('Express Server escutando na porta ' + port);
})