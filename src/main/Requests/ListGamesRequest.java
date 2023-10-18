package Requests;

/**
 * object sent into service
 */

public class ListGamesRequest {
    /**
     * token provided upon request
     */
    private String authtoken;

    /**
     * using this object the service will have all the info needed to
     * complete the task and return a response
     * @param token
     */
    public ListGamesRequest(String token){
        authtoken = token;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }
}
