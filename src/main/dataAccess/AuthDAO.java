package dataAccess;

import ServerModels.AuthtokenModel;

import java.util.HashMap;
import java.util.UUID;

public class AuthDAO {
    public static HashMap<String,String> auths = new HashMap<>();
    /**
     * the clear method clears all authorization tokens from the DB
     */
    public void clear() throws DataAccessException{
        if(!auths.isEmpty()){
            auths.clear();
        }
    }

    /**
     * uses the auth token to delete the authtoken from the database to end the session and log out the user
     * @param authtoken
     * @throws DataAccessException
     */
    public void delete(String authtoken) throws DataAccessException {
        auths.remove(authtoken);
    }

    /**
     * creates an authorization token and stores it in the database
     * @param username
     * @throws DataAccessException
     */
    public String create(String username) throws DataAccessException {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        auths.put(uuidAsString, username);
        return uuidAsString;
    }

    /**
     * function will return a boolean to verify the authorization
     * @param authtoken
     * @throws DataAccessException
     */
    public boolean exists(String authtoken) throws DataAccessException{
        if (auths.containsKey(authtoken)){
            return true;
        }
        else{
            throw new DataAccessException("Error: unauthorized" );
        }
    }
    public String find(String authtoken) throws DataAccessException{
        if (auths.containsKey(authtoken)){
            return auths.get(authtoken);
        }
        else{
            throw new DataAccessException("Error: bad request" );
        }
    }
}
