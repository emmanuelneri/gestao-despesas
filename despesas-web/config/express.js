var express = require('express');

module.exports = function() {
	var app = express();
	
	app.set('port', 3000);
	app.use(express.static('./app'));
	app.use('/lib', express.static('./lib'));

	return app;
}
