/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VertxHttpServerVerticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.JksOptions;
import io.vertx.core.net.PemKeyCertOptions;
import io.vertx.ext.web.Router;

/**
 *
 * @author Niklas
 */
public class VertxHttpServer extends AbstractVerticle {
    public void start() {
        System.out.println("starting VertxHttpServer");
        HttpServer server = vertx.createHttpServer(); //new HttpServerOptions().setSsl(true)
        Router router = Router.router(vertx);
        router.route().method(HttpMethod.POST).path("/chunk").handler(routingContext -> {
            routingContext.request().bodyHandler(bHandler -> {
                HttpServerResponse response = routingContext.response();
                response.putHeader("content-type", "text/plain");
                response.putHeader("Access-Control-Allow-Origin", "*");
                response.putHeader("Access-Control-Allow-Methods", "POST, GET");      
                response.putHeader("Custom-Header", "Own-Data");
                response.putHeader("Access-Control-Expose-Headers", "Custom-Header");
                response.end(bHandler.toString());
            });
        });
        
        router.route().method(HttpMethod.GET).path("/").handler(routingContext -> {
            routingContext.request().bodyHandler(bHandler -> {
                HttpServerResponse response = routingContext.response();
                response.putHeader("content-type", "text/plain");
                response.putHeader("Access-Control-Allow-Origin", "*");
                response.putHeader("Access-Control-Allow-Methods", "POST, GET");      
                response.putHeader("Custom-Header", "Own-Data");
                response.putHeader("Access-Control-Expose-Headers", "Custom-Header");
                System.out.println("respoingoin");
                response.end("From Vertx Http Server hullo");
            });
        });
        server.requestHandler(router::accept).listen(8080);
    }
    
    @Override
    public void stop() {
        System.out.println("VertxHttpServer stopped");
    }
}
