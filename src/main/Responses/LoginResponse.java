package Responses;

/**
 * used as an object to send info back to the front end
 */
public class LoginResponse {
    /**
     * status to return to front end
     */
    private int status;
    /**
     * messaging associated with status
     */
    private String message;

    /**
     * returns a 200 upon success along with username and auth token
     *otherwise reports an error with 400,404, or 500 along with message
     * @param stat
     * @param mes
     */
    public LoginResponse(int stat, String mes){
        status = stat;
        message = mes;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
