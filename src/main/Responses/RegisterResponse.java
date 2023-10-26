package Responses;

/**
 * creates an object to be sent back to front end
 */
public class RegisterResponse {
    /**
     * info needed in the front end or regaurding error
     */
     private String message = null;
     private String username = null;
     private String authToken = null;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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
}
