package Requests;

/**
 * object to be passed into the register service
 */
public class RegisterRequest {
    /**
     * password sent from frontend
     */
    private String password;
    /**
     * email sent from front end
     */
    private String email;
    /**
     * username sent from front end
     */
    private String username;

    /**
     * object that is sent into the register service to fulfill a post request.
     * @param un username
     * @param pw password
     * @param em email
     */
    public RegisterRequest(String un, String pw, String em){
        username = un;
        password = pw;
        email = em;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
