package handlers;

import Requests.ListGamesRequest;
import Responses.ListGamesResponse;
import Server.ChessServer;
import Services.ListGamesService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;

public class ListGameHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        ListGamesRequest myReq = new ListGamesRequest();
        myReq.setAuthorization(request.headers("authorization"));
        ListGamesResponse myRes = new ListGamesService().list(myReq);
        response.status(ChessServer.getStatusCode(myRes.getMessage()));
        return new Gson().toJson(myRes);
    }
}
