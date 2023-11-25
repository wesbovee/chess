package ServerModels;
/**
 * this class creates an object to allow
 * authorization between the user and the database
 *
 */
public class AuthtokenModel {
    /**
     * token assosiated with user account
     */
    private String authToken;
    /**
     * username assosiated with user account
     */
    private String username;

    /**
     *creates the object for authorizations
     * @param auth is the token for user making request
     * @param un username assigned to the user making request
     */

    public AuthtokenModel(String auth, String un){
        authToken = auth;
        username = un;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
