package Responses;

/**
 * used to communicate with front end
 */
public class ClearResponse {
    /**
     * message regarding status of request
     */
    public String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
