package Responses;

import ServerModels.GameModel;
import chess.ChessGame;

import java.util.Collection;

/**
 * sent to the front end to inform on backend activity
 */

public class ListGamesResponse {

    public Collection<GameModel> getGames() {
        return games;
    }

    public void setGames(Collection<GameModel> games) {
        this.games = games;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * collection of games to be listed
     */
    Collection<GameModel> games;
    /**
     * messaging in case of error
     */
    String message;

    /**
     * the response returns a 200 upon success with a list of games to be displayed or listed on screen
     * otherwise it returns a 401 or 500 along with messaging
     * @param stat
     * @param g
     * @param mes
     */

}
