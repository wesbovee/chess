package Responses;

/**
 * used to communicate with front end
 */
public class ClearResponse {
    /**
     * code for status of request
     */
    private int status;
    /**
     * message regarding status of request
     */
    private String message;
    /**
     * responds with a 200 on success or
     * 500 on failure with a message and error description
     * @param status
     * @param mes
     */
    public ClearResponse(int status, String mes){
        this.status = status;
        message = mes;
    }
}
