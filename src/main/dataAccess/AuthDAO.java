package dataAccess;

import ServerModels.AuthtokenModel;

public class AuthDAO {
    /**
     * the clear method clears all authorization tokens from the DB
     */
    public void clear() throws DataAccessException { }

    /**
     * uses the auth token to delete the authtoken from the database to end the session and log out the user
     * @param authtoken
     * @throws DataAccessException
     */
    public void delete(String authtoken) throws DataAccessException {}

    /**
     * creates an authorization token and stores it in the database
     * @param username
     * @throws DataAccessException
     */
    public int create(String username) throws DataAccessException {
        return 0;
    }

    /**
     * function will return a boolean to verify the authorization
     * @param authtoken
     * @throws DataAccessException
     */
    public boolean exists(String authtoken) throws DataAccessException{
        return false;
    }
}
