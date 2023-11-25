package Services;

import Requests.RegisterRequest;
import Responses.RegisterResponse;
import ServerModels.UserModel;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.UserDAO;

/**
 * Service will register a new user and store that information in the Database
 */

public class RegisterService {
    /**
     *  * this function will call the find function to check if that username is already
     *  taken and if it is not then use the UserDAO.create function to construct and
     *  save in database then the AuthDAO.create to start a session and return the
     *  username and an authToken as a response object
     * @param request
     * @return
     */
    public RegisterResponse register(RegisterRequest request){
        RegisterResponse response = new RegisterResponse() ;
        String username = request.getUsername();
        String password = request.getPassword();
        String email = request.getEmail();
        if(username == null || password == null || email == null){
            response.setMessage("Error: bad request");
            return response;
        }else{
            UserModel possibleUser = new UserModel(username,password,email);
            try {
                new UserDAO().create(possibleUser);
                response.setUsername(username);
                response.setAuthToken(new AuthDAO().create(username));
            }
            catch (DataAccessException e){
                response.setMessage(e.getMessage());
            }
        }
        return response;
    }

}