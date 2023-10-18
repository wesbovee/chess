package Services;

import Requests.RegisterRequest;
import Responses.RegisterResponse;

/**
 * Service will register a new user and store that information in the Database
 */

public class RegisterService {
    /**
     *  * this function will call the find function to check if that username is already
     *  taken and if it is not then use the UserDAO.create function to construct and
     *  save in database then the AuthDAO.create to start a session and return the
     *  username and an authToken as a response object
     * @param r
     * @return
     */
    public RegisterResponse register(RegisterRequest r) {
        return null;
    }

}
