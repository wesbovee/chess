package handlers;

import Responses.ClearResponse;
import Services.ClearAppService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;

public class ClearHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        ClearResponse myRes =  new ClearAppService().clearApplication();
        response.status(200);
        return new Gson().toJson(myRes);
    }
}
