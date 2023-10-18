package Services;

import Responses.ClearResponse;

/**
 * clears the database of all usernames, games and authorization tokens
 */
public class ClearAppService {
    /**
     * this function will be called to clear all info from database.
     * it will accomplish this by calling all the clear functions for the DAOs
     */
    public ClearResponse clearApplication() {return null;}
}
