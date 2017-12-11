/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vertxverticles;

import io.vertx.core.AbstractVerticle;
import groovy.sql.Sql;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.asyncsql.MySQLClient;
import io.vertx.ext.sql.SQLClient;
/**
 *
 * @author fauzianordlund
 */
public class VertXDBTest extends AbstractVerticle{
   JsonObject mySQLClientConfig = new JsonObject().put("host", "mymysqldb.mycompany");
   SQLClient mySQLClient = MySQLClient.createShared(vertx, mySQLClientConfig);

   JsonObject p
}
