package handlers;

import Requests.JoinGameRequest;
import Responses.JoinGameResponse;
import Server.ChessServer;
import Services.JoinGameService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;

public class JoinGameHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        JoinGameRequest myReq = new Gson().fromJson(request.body(), JoinGameRequest.class);
        myReq.setAuthorization(request.headers("authorization"));
        JoinGameResponse myRes = new JoinGameService().joinGame(myReq);
        response.status(ChessServer.getStatusCode(myRes.getMessage()));
        return new Gson().toJson(myRes);
    }
}
