/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vertxverticles;

import io.vertx.core.Vertx;

/**
 *
 * @author Niklas
 */
public class StartVerticles {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new VertxSocketServer());
        vertx.deployVerticle(new VertxHttpServer());
//        vertx.deployVerticle(new VertxDB());
    }
    
}
