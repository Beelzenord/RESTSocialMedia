###
# vert.x docker example using a Java verticle
# To build:
#  docker build -t sample/vertx-java .
# To run:
#   docker run -t -i -p 8080:8080 sample/vertx-java
###

# Extend vert.x image
FROM vertx/vertx3

ENV VERTICLE_NAME VertxGetGraphVerticle.VertxGetGraph
ENV VERTICLE_FILE dist/VertxGetGraph.jar

# Set the location of the verticles
ENV VERTICLE_HOME /usr/verticles

EXPOSE 8085

# Copy your verticle to the container
COPY $VERTICLE_FILE $VERTICLE_HOME/
#COPY $VERTICLE_NAME $VERTICLE_HOME/

# Launch the verticle
WORKDIR $VERTICLE_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["exec vertx run $VERTICLE_NAME -cp $VERTICLE_HOME/*"]

# install mysql connector
RUN curl -L -o /mysql-connector-java-5.1.40.jar https://repo1.maven.org/maven2/mysql/mysql-connector-java/5.1.40/mysql-connector-java-5.1.40.jar
ENV CLASSPATH=/mysql-connector-java-5.1.40.jar:${CLASSPATH}