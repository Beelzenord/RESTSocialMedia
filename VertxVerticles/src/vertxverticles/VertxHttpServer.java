/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vertxverticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

/**
 *
 * @author Niklas
 */
public class VertxHttpServer extends AbstractVerticle {
    public void start() {
        System.out.println("starting VertxHttpServer");
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);

        router.route().method(HttpMethod.POST).path("/chunk").handler(routingContext -> {
            routingContext.request().bodyHandler(bHandler -> {
                HttpServerResponse response = routingContext.response();
                response.putHeader("content-type", "text/plain");
                response.putHeader("Access-Control-Allow-Origin", "*");
                response.putHeader("Access-Control-Allow-Methods", "POST, GET");      
                response.putHeader("Custom-Header", "Own-Data");
                response.putHeader("Access-Control-Expose-Headers", "Custom-Header");
                response.end("Hello World from Vert.x-Web! " + bHandler.toString());
            });
        });
        server.requestHandler(router::accept).listen(8083); 
    }
}
