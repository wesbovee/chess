package MyPhase3Tests;

import org.junit.jupiter.api.*;
import passoffTests.TestFactory;
import passoffTests.obfuscatedTestClasses.TestServerFacade;
import passoffTests.testClasses.TestModels;

public class LoginTests {
    private static final int HTTP_OK = 200;
    private static final int HTTP_BAD_REQUEST = 400;
    private static final int HTTP_UNAUTHORIZED = 401;
    private static final int HTTP_FORBIDDEN = 403;

    private static TestModels.TestUser existingUser;
    private static TestModels.TestUser newUser;
    private static TestModels.TestCreateRequest createRequest;
    private static TestServerFacade serverFacade;
    private String existingAuth;


    @BeforeAll
    public static void init() {
        existingUser = new TestModels.TestUser();
        existingUser.username = "Joseph";
        existingUser.password = "Smith";
        existingUser.email = "urim@thummim.net";

        newUser = new TestModels.TestUser();
        newUser.username = "testUsername";
        newUser.password = "testPassword";
        newUser.email = "testEmail";

        createRequest = new TestModels.TestCreateRequest();
        createRequest.gameName = "testGame";

        serverFacade = new TestServerFacade("localhost", TestFactory.getServerPort());
    }


    @BeforeEach
    public void setup() {
        serverFacade.clear();

        TestModels.TestRegisterRequest registerRequest = new TestModels.TestRegisterRequest();
        registerRequest.username = existingUser.username;
        registerRequest.password = existingUser.password;
        registerRequest.email = existingUser.email;

        //one user already logged in
        TestModels.TestLoginRegisterResult regResult = serverFacade.register(registerRequest);
        existingAuth = regResult.authToken;
        // logout
        serverFacade.logout(existingAuth);
    }
    @Test
    @DisplayName("Positive Login test")
    void testLoginPositive(){
        TestModels.TestLoginRequest liRequest = new TestModels.TestLoginRequest();
        liRequest.username = existingUser.username;
        liRequest.password = existingUser.password;
        TestModels.TestLoginRegisterResult registerResult = serverFacade.login(liRequest);

        Assertions.assertEquals(HTTP_OK, serverFacade.getStatusCode(), "Server response code was not 200 OK");

        Assertions.assertEquals(existingUser.username, registerResult.username, "Username returned does not match");
        Assertions.assertNotNull(registerResult.authToken, "no authToken was returned");

        serverFacade.clear();
    }

    @Test
    @DisplayName("Negative Login test")
    void testLoginNegative(){
        TestModels.TestLoginRequest liRequest = new TestModels.TestLoginRequest();
        liRequest.username = "BrotherSB";
        liRequest.password = "pantssquared";
        TestModels.TestLoginRegisterResult registerResult = serverFacade.login(liRequest);

        Assertions.assertNotEquals(HTTP_OK, serverFacade.getStatusCode(), "Server response code was 200 ");

    }
}
