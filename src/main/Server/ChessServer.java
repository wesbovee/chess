package Server;

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
        Spark.delete("/db",(req, res) -> new handlers.ClearHandler().clear(req, res));
        Spark.post("/user",(req, res) -> new handlers.RegisterHandler().register(req,res));
        Spark.post ("/session", (req, res) -> new handlers.LoginHandler().login(req, res));
        Spark.delete ("/session",(req, res) -> new handlers.LogoutHandler().logout(req, res));
        Spark.get("/game", (req, res) -> new handlers.ListGameHandler().list(req, res));
        Spark.post("/game", (req, res) -> new handlers.CreateGameHandler().create(req, res));
        Spark.put("/game", (req, res) -> new handlers.JoinGameHandler().join(req,res));
    }
}
