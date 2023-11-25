package Responses;

/**
 * used as an object to send info back to the front end
 */
public class LoginResponse {
    /**
     * status to return to front end
     */
    private String username = null;
    private String authToken = null;
    /**
     * messaging associated with status
     */
    private String message = null;

    /**
     * returns a 200 upon success along with username and auth token
     *otherwise reports an error with 400,404, or 500 along with message
     *
     */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
