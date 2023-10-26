package Responses;

import ServerModels.GameModel;
import chess.ChessGame;

import java.util.ArrayList;
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
    Collection<GameModel> games = null;
    /**
     * messaging in case of error
     */
    String message = null ;


}
