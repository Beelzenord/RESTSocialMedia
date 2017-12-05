import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
/**
 *
 * @author fauzianordlund
 */
public class VertXServer extends AbstractVerticle{
    private static final Logger LOG = LoggerFactory.getLogger(VertXServer.class);
    //Router rotuer;
     @Override
    public void start(Future<Void> future) {
      
      Router router = Router.router(vertx);

      // Bind "/" to our hello message - so we are still compatible.
      router.route("/").handler(routingContext -> {
      HttpServerResponse response = routingContext.response();
      response
      .putHeader("content-type", "text/html")
      .end("<h1>Hello from my first Vert.x 3 application</h1>");
      });
      vertx
     .createHttpServer()
     .requestHandler(router::accept)
     .listen(
         // Retrieve the port from the configuration,
         // default to 8080.
         config().getInteger("http.port", 8082),
         result -> {
           if (result.succeeded()) {
             future.complete();
           } else {
             future.fail(result.cause());
           }
         }
     );
      EventBus eb = vertx.eventBus();  
        
        /*  HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);
        router.route().handler(routingContext -> {
        // This handler will be called for every request
        HttpServerResponse response = routingContext.response();
        response.putHeader("content-type", "text/plain");
        // Write to the response and end it
        response.end("Hello World from Vert.x-Web!");
        });
        server.requestHandler(router::accept).listen(8082);
        
        */
        /* //LOGGER.info("Welcome to Vertx");
        
        vertx.createHttpServer().requestHandler(r->r.response().
                end("Welcome to this shit")).listen(8081);
      //HttpServer server = vertx.createHttpServer();
       Context context = vertx.getOrCreateContext();
       if (context.isEventLoopContext()) {
       System.out.println("Context attached to Event Loop");
      } else if (context.isWorkerContext()) {
  System.out.println("Context attached to Worker Thread");
      } else if (context.isMultiThreadedWorkerContext()) {
  System.out.println("Context attached to Worker Thread - multi threaded worker");
        } else if (! Context.isOnVertxThread()) {
  System.out.println("Context not attached to a thread managed by vert.x");
         }
       
       long timerID = vertx.setTimer(1000, id -> {
      System.out.println("And one second later this is printed");
    });

       Handler hander; */    
    }

    @Override
    public void stop(Future stopFuture) throws Exception {
        System.out.println("MyVerticle stopped!");
    }
}
