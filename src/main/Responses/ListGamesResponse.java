package Responses;

import chess.ChessGame;

import java.util.Collection;

/**
 * sent to the front end to inform on backend activity
 */

public class ListGamesResponse {
    /**
     * status of backend activity
     */
    int status;
    /**
     * collection of games to be listed
     */
    Collection<ChessGame> games;
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
    public ListGamesResponse(int stat, Collection<ChessGame> g, String mes){
        status = stat;
        games = g;
        message = mes;
    }
}
