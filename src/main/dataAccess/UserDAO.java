package dataAccess;

import ServerModels.UserModel;

public class UserDAO {
    /**
     * the clear method clears all users from the DB
     */
    public void clear() throws DataAccessException { }

    /**
     * registers the user and adds info into the DB
     * @param user
     * @throws DataAccessException
     */
    public void create(UserModel user) throws DataAccessException {}

    /**
     * this function aids in logging in by
     * 1. finding the account being referred
     * 2. returning said user
     * @param username
     * @return UserModel
     * @throws DataAccessException
     */
    public UserModel find(String username) throws DataAccessException{
        return null;
    }


}
