package Requests;

/**
 * created object to be sent to service
 */

public class CreateGameRequest {
    /**
     * token provided by front end
     */
    String authtoken;
    /**
     * name to be assigned to game
     */
    String gameName;

    /**
     * contains information necessary for the service to complete its job
     * @param token
     * @param name
     */
    public CreateGameRequest(String token, String name){
        authtoken = token;
        gameName = name;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }
}
