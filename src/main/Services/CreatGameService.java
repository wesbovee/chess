package Services;

import Requests.CreateGameRequest;
import Responses.CreateGameResponse;

/**
 * Class creates and stores a game using the GameDAO and its methods
 */
public class CreatGameService {
    /**
     * calls the AuthDAO.exists to make sure that the action may be performed then calls GameDAO.create
     * and stores it in the database then returns the game ID as a response object
     * @param r
     * @return
     */
    public CreateGameResponse createGame(CreateGameRequest r) {
        return null;
    }
}
