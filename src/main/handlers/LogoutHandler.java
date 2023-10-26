package handlers;

import Requests.LogoutRequest;
import Responses.LogoutResponse;
import Server.ChessServer;
import Services.LogoutService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

public class LogoutHandler {
    public String logout(Request req, Response res){
        LogoutRequest myReq =new LogoutRequest();
        myReq.setAuthorization(req.headers("authorization"));
        LogoutResponse myRes = new LogoutService().logout(myReq);
        res.body(new Gson().toJson(myRes));
        res.status(ChessServer.getStatusCode(myRes.getMessage()));
        return res.body();
    }
}
