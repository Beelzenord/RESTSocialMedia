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
import io.vertx.core.json.JsonObject;
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

        server.websocketHandler(new Handler<ServerWebSocket>() {
            public void handle(ServerWebSocket ws) {
                System.out.println("new connection to VertxSocketServer");
                
                ws.handler(event -> {
                    ws.write(Buffer.buffer(event.toString()));
                });
            }
        }).listen(8082, "localhost");
    }
    
    @Override
    public void stop() {
        
    }
}
