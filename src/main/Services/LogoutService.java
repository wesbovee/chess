package Services;

import Requests.LogoutRequest;
import Responses.LogoutResponse;

/**
 * class instantiates a method form the AuthDAO in order to fulfill the request
 */
public class LogoutService {
    /**
     * logout calls the AuthDAO.exists to make sure that the action may be performed then calls
     * AuthDAO.delete in order to delete the authtoken from
     * the database ending the session, it returns nothing upon success
     * @param r
     */
    public LogoutResponse logout(LogoutRequest r) {
        return null;
    }
}
