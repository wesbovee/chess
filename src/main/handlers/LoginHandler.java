package handlers;

import Requests.LoginRequest;
import Responses.LoginResponse;
import Server.ChessServer;
import Services.LoginService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

public class LoginHandler {
    public String login(Request req, Response res){
        LoginRequest myReq = new Gson().fromJson(req.body(), LoginRequest.class);
        LoginResponse myRes = new LoginService().login(myReq);
        res.body(new Gson().toJson(myRes));
        res.status(ChessServer.getStatusCode(myRes.getMessage()));
        return res.body();
    }
}
