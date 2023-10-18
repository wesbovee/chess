package Requests;

/**
 * Object passed into the login service
 */

public class LoginRequest {
    /**
     * username privided on front end
     */
    private String username;
    /**
     * password provided on front end
     */
    private String password;

    /**
     * contains all parameters necessary to complete service
     */
    public LoginRequest(String un, String pw){
        username = un;
        password = pw;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
