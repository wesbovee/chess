package Services;

import Requests.JoinGameRequest;
import Responses.JoinGameResponse;

/**
 * Verifies that the specified game exists, and, if a color is
 * specified, adds the caller as the requested color to the game.
 * If no color is specified the user is joined as an observer.
 * This request is idempotent.
 */

public class JoinGameService {
    /**
     * calls the AuthDAO.exists to make sure that the action may be performed then calls
     * GameDAO.find to make sure the game exists. if the user has submitted a color,
     * the result of the find method is then altered to change the color to username of
     * the user making the request then uses the update to store it with the same id
     * @param r
     */
    public JoinGameResponse joinGame (JoinGameRequest r) {return null;}
}
