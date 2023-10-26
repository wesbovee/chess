package dataAccess;

import ServerModels.UserModel;

import java.util.HashMap;
import java.util.HashSet;

public class UserDAO {
    public static HashMap<String, UserModel> users = new HashMap<>();
    public static HashSet<String> emails = new HashSet<>();
    /**
     * the clear method clears all users from the DB
     */
    public void clear() throws DataAccessException{
        if(!users.isEmpty()){
            users.clear();
        }
        if(!emails.isEmpty()){
            emails.clear();
        }
    }

    /**
     * registers the user and adds info into the DB
     * @param user
     * @throws DataAccessException
     */
    public void create(UserModel user) throws DataAccessException {
        String email = user.getEmail();
        String username = user.getUsername();
        if (emails.contains(email)||users.containsKey(username)){
            throw new DataAccessException("Error: already taken");
        }
        users.put(username, user);
    }

    /**
     * this function aids in logging in by
     * 1. finding the account being referred
     * 2. returning said user
     * @param username
     * @return UserModel
     * @throws DataAccessException
     */
    public UserModel find(String username) throws DataAccessException{
        if (users.containsKey(username)){
            return users.get(username);
        }else {
            throw new DataAccessException("Error: unauthorized");
        }
    }


}
