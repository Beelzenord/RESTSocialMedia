/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VertxSocketDBVerticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Niklas
 */
public class VertxSocketDB extends AbstractVerticle {
    public void start() {
        System.out.println("starting VertxSocketDB");
        HttpServer server = vertx.createHttpServer();
        
        server.websocketHandler(new Handler<ServerWebSocket>() {
            public void handle(ServerWebSocket ws) {
                System.out.println("new connection to VertxSocketDB");
                ws.handler(event -> {
                    JsonObject joson = event.toJsonObject();
                    addGraphToDB(joson);
                    
                    ws.write(Buffer.buffer(event.toString()));
                });
            }
        }).listen(8084);
    }
    
    private void addGraphToDB(JsonObject json) {
        vertx.executeBlocking(future -> {
            try {
                Long key = null;
                Connection myConn = DriverManager.getConnection("jdbc:mysql://192.168.99.100:32769/socialmedia?useSSL=false", "root", "root");
                
                java.sql.ResultSet rs = null;
                PreparedStatement p = null;
                String graph = "INSERT INTO t_graphs (Sender_id, graphType)"
                        + "VALUES(?, ?)";
                p = myConn.prepareStatement(graph, Statement.RETURN_GENERATED_KEYS);
                String tmpid = json.getString("userid");
                Long id = Long.parseLong(tmpid);
                String graphType = json.getString("theChartType");
                p.setLong(1, id);
                p.setString(2, graphType);
                p.executeUpdate();
                rs = p.getGeneratedKeys();
                while (rs.next()) {
                    key = rs.getLong(1);
                }
                JsonArray socketAsync = json.getJsonArray("theSocketAsync");
                JsonArray socketSync = json.getJsonArray("theSocketSync");
                JsonArray httpAsync = json.getJsonArray("theHttpAsync");
                JsonArray httpSync = json.getJsonArray("theHttpSync");
                test(socketAsync, myConn, key, createPrep());
                test(socketSync, myConn, key, createPrep());
                test(httpAsync, myConn, key, createPrep());
                test(httpSync, myConn, key, createPrep());
                
            } catch (SQLException ex) {
            }
            future.complete();
        }, res -> {
            
        });
    }
    
    private String createPrep() {
        return "INSERT INTO t_graphstats (Graph_id, duration, label)"
                        + "VALUES(?, ?, ?)";
    }
    
    private void test(JsonArray array, Connection conn, Long graphid, String sqlstring) {
        try {
            PreparedStatement p = conn.prepareStatement(sqlstring);
            for (int i = 0; i < array.size(); i++) {
                JsonObject tmpObj = array.getJsonObject(i);
                p.setLong(1, graphid);
                p.setDouble(2, tmpObj.getDouble("duration"));
                p.setString(3, tmpObj.getString("label"));
                p.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(VertxSocketDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void stop() {
        System.out.println("VertxSocketDB stopped");
    }
}
