package Requests;

import chess.ChessGame;

/**
 * creats an object to be sent to the service
 */

public class JoinGameRequest {
    /**
     * token provided by front end
     */
    String authtoken;
    /**
     * color of desired team
     */
    private ChessGame.TeamColor col;
    /**
     * game wanting to join
     */
    private int gameID;

    /**
     * contains info necesary to complete the request
     * @param token
     * @param c
     * @param gid
     */
    public JoinGameRequest(String token, ChessGame.TeamColor c, int gid){
        col = c;
        gameID = gid;
        authtoken = token;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public ChessGame.TeamColor getCol() {
        return col;
    }

    public void setCol(ChessGame.TeamColor col) {
        this.col = col;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }
}
