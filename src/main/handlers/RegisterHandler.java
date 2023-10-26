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
//    public String register(Request req, Response res){
//        RegisterRequest myReq = new Gson().fromJson(req.body(), RegisterRequest.class);
//        RegisterResponse myRes = new RegisterService().register(myReq);
//        res.body(new Gson().toJson(myRes));
//        res.status(ChessServer.getStatusCode(myRes.getMessage()));
//        return res.body();
//    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        RegisterRequest myReq = new Gson().fromJson(request.body(), RegisterRequest.class);
        RegisterResponse myRes = new RegisterService().register(myReq);
        response.status(ChessServer.getStatusCode(myRes.getMessage()));
        return new Gson().toJson(myRes);
    }
}
