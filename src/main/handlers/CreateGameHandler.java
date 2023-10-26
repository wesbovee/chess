package handlers;

import Requests.CreateGameRequest;
import Responses.CreateGameResponse;
import Server.ChessServer;
import Services.CreateGameService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

public class CreateGameHandler {
    public String create(Request req, Response res){
        CreateGameRequest myReq = new Gson().fromJson(req.body(), CreateGameRequest.class);
        myReq.setAuthorization(req.headers("authorization"));
        CreateGameResponse myRes = new CreateGameService().createGame(myReq);
        if (myRes.getGameID() != 0 ){
            res.body(new Gson().toJson(myRes));
        }
        res.status(ChessServer.getStatusCode(myRes.getMessage()));
        return res.body();
    }
}
