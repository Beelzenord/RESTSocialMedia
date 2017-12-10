/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vertxverticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.streams.Pump;

/**
 *
 * @author Niklas
 */
public class VertxSocketServer extends AbstractVerticle {
    @Override
    public void start() {
        System.out.println("starting VertxSocketServer");
        HttpServer server = vertx.createHttpServer();

        /*server.requestHandler(new Handler<HttpServerRequest>() {
            public void handle(HttpServerRequest req) {
                req.bodyHandler(event -> {
                   req.response().end(event.toString());
                });
            }
        }).listen(8083, "localhost");*/
        
        server.websocketHandler(new Handler<ServerWebSocket>() {
            public void handle(ServerWebSocket ws) {
                System.out.println("new connection to VertxSocketServer");
                // A WebSocket has connected!
                //Pump.pump(ws, ws).start();
                //ws.write(Buffer.buffer("from java server"));
                
                ws.handler(event -> {
                    //System.out.println(event.toString());
                    // when something is sent to this particular socket
                    //ws.write(Buffer.buffer("new messages received.. " + event.toString()));
                    ws.writeTextMessage(event.toString());
                });
            }
        }).listen(8082, "localhost");
    }
    
    @Override
    public void stop() {
        
    }
}
