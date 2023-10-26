package handlers;

import Requests.CreateGameRequest;
import Responses.CreateGameResponse;
import Responses.LogoutResponse;
import Server.ChessServer;
import Services.CreateGameService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;

public class CreateGameHandler implements Route {
//    public String create(Request req, Response res){
//        CreateGameRequest myReq = new Gson().fromJson(req.body(), CreateGameRequest.class);
//        myReq.setAuthorization(req.headers("authorization"));
//        CreateGameResponse myRes = new CreateGameService().createGame(myReq);
//        if (myRes.getGameID() != 0 ){
//            res.body(new Gson().toJson(myRes));
//        }
//        res.status(ChessServer.getStatusCode(myRes.getMessage()));
//        return res.body();
//    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        CreateGameRequest myReq = new Gson().fromJson(request.body(), CreateGameRequest.class);
        myReq.setAuthorization(request.headers("authorization"));
        CreateGameResponse myRes = new CreateGameService().createGame(myReq);
        if(myRes.getGameID() == 0){
            LogoutResponse myTrueRes = new LogoutResponse();
            myTrueRes.setMessage(myRes.getMessage());
            response.status(ChessServer.getStatusCode(myTrueRes.getMessage()));
            return new Gson().toJson(myTrueRes);
        }
        response.status(ChessServer.getStatusCode(myRes.getMessage()));
        return new Gson().toJson(myRes);
    }
}
