package Requests;

import chess.ChessGame;

/**
 * creats an object to be sent to the service
 */

public class JoinGameRequest {
    /**
     * token provided by front end
     */
    String authorization;
    /**
     * color of desired team
     */
    private ChessGame.TeamColor playerColor;
    /**
     * game wanting to join
     */
    private int gameID;

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public ChessGame.TeamColor getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(ChessGame.TeamColor playerColor) {
        this.playerColor = playerColor;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }
}