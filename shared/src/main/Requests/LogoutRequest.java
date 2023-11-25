package Requests;

/**
 * sent to the service with all things used to complete service
 */
public class LogoutRequest {
    /**
     * token provided by front end
     */
    private String authorization = null;

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }
}
