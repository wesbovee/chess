package Services;

import Requests.CreateGameRequest;
import Responses.CreateGameResponse;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;

/**
 * Class creates and stores a game using the GameDAO and its methods
 */
public class CreateGameService {
    /**
     * calls the AuthDAO.exists to make sure that the action may be performed then calls GameDAO.create
     * and stores it in the database then returns the game ID as a response object
     * @param request
     * @return
     */
    public CreateGameResponse createGame(CreateGameRequest request) {
        CreateGameResponse response = new CreateGameResponse();
        String gameName = request.getGameName();
        String token = request.getAuthorization();
       try{
           if (new AuthDAO().exists(token)){
               if (gameName == null){
                   response.setMessage("Error: bad request");
               }else {
                   int id = new GameDAO().create(gameName);
                   response.setGameID(id);
               }
           }else {
               response.setMessage("Error: unauthorized");
           }
        }catch (DataAccessException e){
            response.setMessage(e.getMessage());
        }
        return response;
    }
}
