package Services;

import Requests.LoginRequest;
import Responses.LoginResponse;

/**
 * class uses a request and calls methods from authDAO to create an auth token and store
 * it in the Database
 */
public class LoginService {
    /**
     * using the AuthDAO.create, this function will trigger a creation of an auth token
     * and return the username and authtoken as a response object
     * @param r
     * @return
     */
    public LoginResponse login(LoginRequest r) {
        return null;
    }
}
