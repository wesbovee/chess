package Responses;

/**
 * used to communicate with front end
 */
public class ClearResponse {
    /**
     * responds with a 200 on success or
     * 500 on failure with a message and error description
     */
    private int status;
    public ClearResponse(int status){
        this.status = status;
    }
}
