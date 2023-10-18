package Requests;

/**
 * sent to the service with all things used to complete service
 */
public class LogoutRequest {
    /**
     * token provided by front end
     */
    private String authtoken;

    /**
     * package of info sent into the Service
     * @param token
     */
    public LogoutRequest(String token){
        authtoken = token;
    }
}
