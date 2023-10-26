package Services;

import Requests.LogoutRequest;
import Responses.LogoutResponse;
import ServerModels.GameModel;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;

import java.util.ArrayList;
import java.util.Collection;

/**
 * class instantiates a method form the AuthDAO in order to fulfill the request
 */
public class LogoutService {
    /**
     * logout calls the AuthDAO.exists to make sure that the action may be performed then calls
     * AuthDAO.delete in order to delete the authtoken from
     * the database ending the session, it returns nothing upon success
     * @param request
     */
    public LogoutResponse logout(LogoutRequest request) {
        Collection<GameModel> games = new ArrayList<>();
                LogoutResponse response = new LogoutResponse();
        String token = request.getAuthorization();
        try{
            if (new AuthDAO().exists(token)){
                games = new GameDAO().listOGames();
            }
        }catch (DataAccessException e){
            response.setMessage(e.getMessage());
        }
        return response;
    }
}
