/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vertxverticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.asyncsql.*;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.SQLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Niklas
 */
public class VertxDB extends AbstractVerticle {
    public void start() {
        Connection myConn = null;
        try {
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/socialmedia?useSSL=false", "root", "root");
        } catch (SQLException ex) {
            System.out.println("hmm");
            Logger.getLogger(VertxDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        java.sql.ResultSet rs = null;
        PreparedStatement p = null;
        String str = "SELECT * FROM T_Users";
        try {
            p = myConn.prepareStatement(str);
        } catch (SQLException ex) {
            System.out.println("prep");
        }
        try {
            rs = p.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString(3));
            }
        } catch (SQLException ex) {
            Logger.getLogger(VertxDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        System.exit(0);
        
        
        
        //JsonObject mySQLClientConfig = new JsonObject().put("localhost", "socialmedia");
        JsonObject config = new JsonObject()
          .put("url", "jdbc:mysql://localhost:3306/socialmedia?useSSL=false")
          //.put("database", "socialmedia")
          //.put("host", "localhost")
          .put("driver_class", "com.mysql.jdbc.Driver")
          //f.put("max_pool_size", 30)
          .put("username", "root")
          .put("password", "root");
        JDBCClient client = JDBCClient.createShared(vertx, config);
        System.out.println("skldslksd");
        client.getConnection(res -> {
            System.out.println("ins");
            if (res.succeeded()) {
                System.out.println("succ");
                SQLConnection connection = res.result();
                
                connection.query("SELECT * FROM T_Users", res2 -> {
                    if (res2.succeeded()) {
                        System.out.println("hellt");
                        ResultSet rsr = res2.result();
                        System.out.println("the result set\n" + rsr.toString());
                      // Do something with results
                    }
                });
            } 
            else {
                System.out.println("failed");
              // Failed to get connection - deal with it
            }
        });
        //System.exit(0);
    }
}
