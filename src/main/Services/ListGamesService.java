package Services;

import Requests.ListGamesRequest;
import Responses.ListGamesResponse;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;

/**
 * Class calls the necessary functions from authDAO and GameDAO to perform the request
 */

public class ListGamesService {
    /**
     * calls the AuthDAO.exists to make sure that the action may be performed then calls
     * GamesDAO.listOGames to return all games from the Database as a response object
     * @param request
     * @return
     */
    public ListGamesResponse list(ListGamesRequest request){
        ListGamesResponse response = new ListGamesResponse();
        String token = request.getAuthorization();
        try{
            if (new AuthDAO().exists(token)){
                response.setGames(new GameDAO().listOGames());
            }else{
                response.setMessage("Error: unauthorized");
            }
        }catch (DataAccessException e){
            response.setMessage(e.getMessage());
        }
        return response;
    }
}
