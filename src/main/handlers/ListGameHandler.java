package handlers;

import Requests.ListGamesRequest;
import Responses.ListGamesResponse;
import Server.ChessServer;
import Services.ListGamesService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

public class ListGameHandler {
    public String list(Request req, Response res){
        ListGamesRequest myReq = new ListGamesRequest();
        myReq.setAuthorization(req.headers("authorization"));
        ListGamesResponse myRes = new ListGamesService().list(myReq);
        res.body(new Gson().toJson(myRes));
        res.status(ChessServer.getStatusCode(myRes.getMessage()));
        return res.body();
    }
}
