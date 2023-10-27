package handlers;

import Requests.LogoutRequest;
import Responses.LogoutResponse;
import Server.ChessServer;
import Services.LogoutService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;

public class LogoutHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        LogoutRequest myReq =new LogoutRequest();
        myReq.setAuthorization(request.headers("authorization"));
        LogoutResponse myRes = new LogoutService().logout(myReq);
        response.status(ChessServer.getStatusCode(myRes.getMessage()));
        return new Gson().toJson(myRes);
    }
}
