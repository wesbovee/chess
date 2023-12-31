package MyPhase3Tests;

import dataAccess.AuthDAO;
import org.junit.jupiter.api.*;
import passoffTests.TestFactory;
import passoffTests.obfuscatedTestClasses.TestServerFacade;
import passoffTests.testClasses.TestModels;

public class RegisterTests {
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
    }
    @Test
    @DisplayName("Positive Registration test")
    void testRegistrationPositive(){
        TestModels.TestRegisterRequest registerRequest = new TestModels.TestRegisterRequest();
        registerRequest.username = "BrotherSB";
        registerRequest.password = "pantssquared";
        registerRequest.email = "pineapple@under.sea";
        TestModels.TestLoginRegisterResult registerResult = serverFacade.register(registerRequest);

        Assertions.assertEquals(HTTP_OK, serverFacade.getStatusCode(), "Server response code was not 200 OK");

        Assertions.assertEquals("BrotherSB", registerResult.username, "Username returned does not match");
        Assertions.assertNotNull(registerResult.authToken, "no authToken was returned");
    }

    @Test
    @DisplayName("Negative Registration test")
    void testRegistrationNegative(){
        TestModels.TestRegisterRequest registerRequest = new TestModels.TestRegisterRequest();
        registerRequest.username = "Joseph";
        registerRequest.password = "Smith";
        registerRequest.email = "urim@thummim.net";
        TestModels.TestLoginRegisterResult registerResult = serverFacade.register(registerRequest);

        Assertions.assertNotEquals(HTTP_OK, serverFacade.getStatusCode(), "Server response code was 200 ");
        serverFacade.clear();
    }
}
