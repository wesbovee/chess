package Responses;

/**
 * used to communicate with front end
 */
public class CreateGameResponse {
    /**
     * id number assigned to the game
     */
    int gameid;
    /**
     * status of backend action
     */
    int status;
    /**
     * message correlated with status
     */
    String message;

    /**
     * responds with a 200 along with the game id
     *      * otherwise returns 400,401, or 500 with messaging
     * @param gid
     * @param stat
     * @param mes
     */
    public CreateGameResponse(int gid, int stat, String mes){
        status= stat;
        message = mes;
        gameid = gid;
    }

}
