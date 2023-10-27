package handlers;

import Requests.RegisterRequest;
import Responses.RegisterResponse;
import Services.RegisterService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import Server.ChessServer;
import spark.Route;


public class RegisterHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        RegisterRequest myReq = new Gson().fromJson(request.body(), RegisterRequest.class);
        RegisterResponse myRes = new RegisterService().register(myReq);
        response.status(ChessServer.getStatusCode(myRes.getMessage()));
        return new Gson().toJson(myRes);
    }
}
