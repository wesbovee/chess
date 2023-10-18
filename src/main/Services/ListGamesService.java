package Services;

import Requests.ListGamesRequest;
import Responses.ListGamesResponse;

/**
 * Class calls the necessary functions from authDAO and GameDAO to perform the request
 */

public class ListGamesService {
    /**
     * calls the AuthDAO.exists to make sure that the action may be performed then calls
     * GamesDAO.listOGames to return all games from the Database as a response object
     * @param r
     * @return
     */
    public ListGamesResponse listGames(ListGamesRequest r){
        return null;
    }
}
