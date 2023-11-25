package Requests;

/**
 * created object to be sent to service
 */

public class CreateGameRequest {
    /**
     * token provided by front end
     */
    String authorization;
    /**
     * name to be assigned to game
     */
    String gameName;

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }
}
