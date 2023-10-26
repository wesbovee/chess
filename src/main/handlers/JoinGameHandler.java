package handlers;

import Requests.JoinGameRequest;
import Responses.JoinGameResponse;
import Server.ChessServer;
import Services.JoinGameService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

public class JoinGameHandler {
    public String join(Request req, Response res){
        JoinGameRequest myReq = new Gson().fromJson(req.body(), JoinGameRequest.class);
        myReq.setAuthorization(req.headers("authorization"));
        JoinGameResponse myRes = new JoinGameService().joinGame(myReq);
        res.status(ChessServer.getStatusCode(myRes.getMessage()));
        return res.body();
    }
}
