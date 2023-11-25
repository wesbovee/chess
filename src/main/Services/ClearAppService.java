package Services;

import Responses.ClearResponse;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.UserDAO;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * clears the database of all usernames, games and authorization tokens
 */
public class ClearAppService {
    /**
     * this function will be called to clear all info from database.
     * it will accomplish this by calling all the clear functions for the DAOs
     */
    public ClearResponse clearApplication() {
        ClearResponse response = new ClearResponse();
        try {
            new AuthDAO().clear();
            new GameDAO().clear();
            new UserDAO().clear();
            return response;
        }
        catch(DataAccessException e){
            response.setMessage(""+e);
            return response;
        }
    }
}
