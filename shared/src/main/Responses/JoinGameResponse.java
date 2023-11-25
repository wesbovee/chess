package Responses;

/**
 * communicates status and activity of backend
 */
public class JoinGameResponse {

    /**
     * accociated message of the status
     */
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
