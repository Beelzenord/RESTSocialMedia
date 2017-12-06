var vertx = require('vertx');
var console = require('vertx/console');
//var pump = require('pump');
var server = vertx.createHttpServer();


server.websocketHandler(function(websocket) {
    console.log("New Connection");

    websocket.write(new vertx.Buffer('hello new connection'));
    websocket.handler(function(event) {
        console.log("websocket.handler in");
        websocket.write(new vertx.Buffer('lul'));
    });
    //new vertx.Pump(websocket, websocket + "hi").start();

}).listen(8083, 'localhost');