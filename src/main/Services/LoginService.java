package Services;

import Requests.LoginRequest;
import Responses.LoginResponse;
import ServerModels.UserModel;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.UserDAO;

/**
 * class uses a request and calls methods from authDAO to create an auth token and store
 * it in the Database
 */
public class LoginService {
    /**
     * using the AuthDAO.create, this function will trigger a creation of an auth token
     * and return the username and authtoken as a response object
     * @param request
     * @return
     */
    public LoginResponse login(LoginRequest request) {
        LoginResponse response = new LoginResponse();
        String username = request.getUsername();
        String password = request.getPassword();
        try{
            UserModel user = new UserDAO().find(username);
            if(user.getPassword().equals(password)){
                response.setAuthToken(new AuthDAO().create(username));
                response.setUsername(username);
            }
            else{
                response.setMessage("Error: unauthorized");
            }
        }catch (DataAccessException e){
            response.setMessage(e.getMessage());
        }
        return response;
    }
}
