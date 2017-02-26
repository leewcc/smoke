var express = require('express');
var app = express();
console.log('service start');
app.get('/', function(req, res){
	console.log('get it');
	console.log(req);
	res.send('{"status": "success"}');
});

app.listen(8080);