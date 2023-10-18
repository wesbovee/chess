package Responses;

/**
 * communicates status and activity of backend
 */
public class JoinGameResponse {
    /**
     * reports status of backend
     */
    int status;
    /**
     * accociated message of the status
     */
    String message;
    /**
     * in the event of a 200 this object returns 200
     * otherwise it may return a 400, 401, 403, or 500 with mesaging about the error
     */
    public JoinGameResponse(int stat, String mes){
        status = stat;
        message = mes;
    }
}
