package Server;

import handlers.*;
import spark.Spark;

import java.util.Map;

public class ChessServer {
    final private static Map<String, Integer> mesCodes = Map.of(
            "Error: bad request", 400,
            "Error: unauthorized", 401,
            "Error: already taken", 403,
            "Error: description", 500);
    public static int getStatusCode(String message){
        if (message != null){
            return mesCodes.get(message);
        }
        return 200;
    }
    public static void main(String[] args) {
        Spark.port(8080);
        System.out.println("Listening on port 8080");
        Spark.externalStaticFileLocation("src/web");
        new ChessServer().directToHandler();

    }
    public void directToHandler() {
        Spark.delete("/db", new ClearHandler());
        Spark.post("/user", new RegisterHandler());
        Spark.post ("/session",new LoginHandler());
        Spark.delete ("/session",new LogoutHandler());
        Spark.get("/game", new ListGameHandler());
        Spark.post("/game", new CreateGameHandler());
        Spark.put("/game", new JoinGameHandler());
    }
}
