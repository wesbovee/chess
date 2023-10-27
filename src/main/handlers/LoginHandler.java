package handlers;

import Requests.LoginRequest;
import Responses.LoginResponse;
import Server.ChessServer;
import Services.LoginService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;

public class LoginHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        LoginRequest myReq = new Gson().fromJson(request.body(), LoginRequest.class);
        LoginResponse myRes = new LoginService().login(myReq);
        response.status(ChessServer.getStatusCode(myRes.getMessage()));
        return new Gson().toJson(myRes);
    }
}
