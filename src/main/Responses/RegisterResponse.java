package Responses;

/**
 * creates an object to be sent back to front end
 */
public class RegisterResponse {
    /**
     * status to be returned to front end
     */
    private int status;
    /**
     * info needed in the front end or regaurding error
     */
     private String message;

    /**
     * if successful returns a 200 with a username and authtoken
     * otherwise returns an error code being either 400,403,or 500
     * @param stat status of service
     * @param mes if success then auth token and username else if an error occurred what happened?
     */
    public RegisterResponse(int stat, String mes){
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
