package ServerModels;

/**
 * this essentially creates the account to be used
 * to interact with the database and potentially a game
 */
public class UserModel {
    /**
     * Username chosen by the user
     */
    private String username;
    /**
     * password for the user
     */
    private String password;
    /**
     * email associated with the users account
     */
    private String email;

    /**
     * creates an object which represents the client in the database.
     * @param un username for user
     * @param pw password for user
     * @param em email for the user
     */
    public UserModel(String un,String pw, String em ){
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
