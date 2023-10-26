package handlers;

import Responses.ClearResponse;
import Services.ClearAppService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import Server.ChessServer;

public class ClearHandler {
    public String clear(Request req, Response res){
        ClearResponse response = new ClearAppService().clearApplication();
        res.body(new Gson().toJson(response));
        res.status(ChessServer.getStatusCode(response.message));
        return res.body();
    }
}
