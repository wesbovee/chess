package Services;

import Requests.JoinGameRequest;
import Responses.CreateGameResponse;
import Responses.JoinGameResponse;
import chess.ChessGame;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;

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
     * @param request
     */
    public JoinGameResponse joinGame (JoinGameRequest request) {
        JoinGameResponse response = new JoinGameResponse();
        int gameID = request.getGameID();
        ChessGame.TeamColor playerColor = request.getPlayerColor();
        String token = request.getAuthorization();

        try {
            if (new AuthDAO().exists(token) && new GameDAO().exists(gameID) && playerColor != null) {
                new GameDAO().claimSpot(new AuthDAO().find(token).getUsername(), playerColor, gameID);
            } else if(!new AuthDAO().exists(token)){
                response.setMessage("Error: unauthorized");
            } else if (!new GameDAO().exists(gameID)) {
                response.setMessage("Error: bad request");
            }
        } catch (DataAccessException e) {
            response.setMessage(e.getMessage());
        }
        return response;
    }
}
