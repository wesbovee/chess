package Responses;

/**
 * used to communicate with front end
 */
public class CreateGameResponse {
    /**
     * id number assigned to the game
     */
    int gameID = 0;

    /**
     * message correlated with status
     */
    String message = null;

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
