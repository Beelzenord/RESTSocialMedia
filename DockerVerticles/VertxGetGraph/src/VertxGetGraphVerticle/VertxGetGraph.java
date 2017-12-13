/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VertxGetGraphVerticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Niklas
 */
public class VertxGetGraph extends AbstractVerticle {
    public void start() {
        System.out.println("starting VertxGetGraph");
        HttpServer server = vertx.createHttpServer();
        
        server.websocketHandler(new Handler<ServerWebSocket>() {
            public void handle(ServerWebSocket ws) {
                System.out.println("new connection to VertxGetGraph");
                ws.handler(event -> {
                    JsonObject joson = event.toJsonObject();
                    String id = joson.getString("id");
                    getGraph(Long.parseLong(id), ws);
                });
            }
        }).listen(8085);
    }
    
    private void getGraph(Long id, ServerWebSocket ws) {
        vertx.executeBlocking(future -> {
            try {
                Connection myConn = DriverManager.getConnection("jdbc:mysql://192.168.99.100:32769/socialmedia?useSSL=false", "root", "root");
                java.sql.ResultSet rs = null;
                PreparedStatement p = null;
                String graph = "SELECT * FROM t_graphs WHERE Sender_id LIKE ?;";
                p = myConn.prepareStatement(graph);
                p.setLong(1, id);
                rs = p.executeQuery();
                Long graphid = null;
                String graphType = "bar";
                while (rs.next()) {
                    graphid = rs.getLong("id");
                    graphType = rs.getString("graphType");
                }
                String graphStats = "SELECT * FROM t_graphstats WHERE Graph_id LIKE ?;";
                PreparedStatement prepGS = myConn.prepareStatement(graphStats);
                prepGS.setLong(1, graphid);
                rs = prepGS.executeQuery();
                JsonObject reObj = new JsonObject();
                reObj.put("graphType", graphType);
                JsonArray reArr = new JsonArray();
                while(rs.next()) {
                    JsonObject tmp = new JsonObject();
                    tmp.put("duration", rs.getDouble("duration"));
                    reArr.add(tmp);
                    JsonObject tmp2 = new JsonObject();
                    tmp2.put("label", rs.getString("label"));
                    reArr.add(tmp2);
                }
                reObj.put("dataPoints", reArr);
                ws.writeTextMessage(reObj.toString());
            } catch (SQLException ex) {
                ws.writeTextMessage("error");
            }
            future.complete();
        }, res -> {
            
        });
    }
    
    @Override
    public void stop() {
        System.out.println("VertxGetGraph stopped");
    }
}
