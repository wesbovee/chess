package Responses;

/**
 * object sent to the front end to report on backend activity
 */

public class LogoutResponse {

    /**
     * messageing associated with status
     */
    private String message = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
/**
     * responds with a 200 if successful
     *      * otherwise returns 401 or 500 with error messaging
     * @param mes
     */

}
