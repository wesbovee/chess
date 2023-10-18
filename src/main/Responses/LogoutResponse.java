package Responses;

/**
 * object sent to the front end to report on backend activity
 */

public class LogoutResponse {
    /**
     * status of service
     */
    private int status;
    /**
     * messageing associated with status
     */
    private String message;

    /**
     * responds with a 200 if successful
     *      * otherwise returns 401 or 500 with error messaging
     * @param stat
     * @param mes
     */
    public LogoutResponse(int stat, String mes) {
        status = stat;
        message = mes;
    }
}
