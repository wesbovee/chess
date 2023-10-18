package ServerModels;

import chess.ChessGame;

/**
 * this class creates a game to be stored in the database which can
 * be accessed with proper authorization upon request.
 */

public class GameModel {
    /**
     * the id number for the game
     */
    private int gameID;
    /**
     * this is the username associated to the player using the white pieces
     */
    private String whiteUsername;
    /**
     * this is the username associated with the user playing with black pieces
     */
    private String blackUsername;
    /**
     * this is the name for the game that is chosen upon creation
     */
    private String gameName;
    /**
     * this is the game object created in the Game class which is to be stored in the Database
     */
    private ChessGame game;

    /**
     * this is the game object and all associated information necesary to keep track of it in the database
     * and to be easily identified by the user.
     * @param gameid number associated with the game
     * @param white white teams user username
     * @param black black teams user username
     * @param name name of the game chosen upon creation
     * @param g game object storing information for the game.
     */
    public GameModel(int gameid, String white, String black, String name, ChessGame g){
        gameID = gameid;
        whiteUsername = white;
        blackUsername = black;
        gameName = name;
        game = g;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) { this.gameID = gameID;}

    public String getWhiteUsername() {
        return whiteUsername;
    }

    public void setWhiteUsername(String whiteUsername) {
        this.whiteUsername = whiteUsername;
    }

    public String getBlackUsername() {
        return blackUsername;
    }

    public void setBlackUsername(String blackUsername) {
        this.blackUsername = blackUsername;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public ChessGame getGame() {
        return game;
    }

    public void setGame(ChessGame game) {
        this.game = game;
    }
}
