package Requests;

/**
 * object sent into service
 */

public class ListGamesRequest {
    /**
     * token provided upon request
     */
    private String authorization;

    /**
     * using this object the service will have all the info needed to
     * complete the task and return a response
     *
     */
    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }
}
