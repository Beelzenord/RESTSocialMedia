/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vertxverticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;

/**
 *
 * @author Niklas
 */
public class VertxHttpServer extends AbstractVerticle {
    public void start() {
        System.out.println("starting VertxHttpServer");
        
        HttpServer server = vertx.createHttpServer();
        server.requestHandler(new Handler<HttpServerRequest>() {
            public void handle(HttpServerRequest req) {
                System.out.println("Http request to VertxHttpServer");
                HttpServerResponse res = req.response();
                //res.setStatusCode(200);
                //res.putHeader("content-type", "text/html");
                res.end("back from VertxHttpServer");
                /*req.bodyHandler(event -> {
                   req.response().end(event.toString() + "forom server ");
                });*/
            }
        }).listen(8083, "localhost");
    }
}
