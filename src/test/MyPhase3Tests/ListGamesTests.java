package MyPhase3Tests;

import Requests.CreateGameRequest;
import Responses.CreateGameResponse;
import org.junit.jupiter.api.*;
import passoffTests.TestFactory;
import passoffTests.obfuscatedTestClasses.TestServerFacade;
import passoffTests.testClasses.TestModels;

public class ListGamesTests {

    private static final int HTTP_OK = 200;
    private static final int HTTP_BAD_REQUEST = 400;
    private static final int HTTP_UNAUTHORIZED = 401;
    private static final int HTTP_FORBIDDEN = 403;

    private static TestModels.TestUser existingUser;
    private static TestModels.TestUser newUser;
    private static TestModels.TestCreateRequest createRequest;
    private static TestServerFacade serverFacade;
    private String existingAuth;
    private int createdID;


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

        createdID = serverFacade.createGame(createRequest, existingAuth).gameID;
    }

    @Test
    @DisplayName("Positive List Games test")
    void testListGamesPositive(){
        TestModels.TestListResult listResult = serverFacade.listGames(existingAuth);

        Assertions.assertEquals(HTTP_OK, serverFacade.getStatusCode(), "Server response code was not 200 OK");
        Assertions.assertEquals(createdID, listResult.games[0].gameID, "Game ID returned does not match the one created.");

    }

    @Test
    @DisplayName("Negative List Games test")
    void testListGamesNegative(){
        TestModels.TestListResult listResult = serverFacade.listGames("existingAuth");

        Assertions.assertNotEquals(HTTP_OK, serverFacade.getStatusCode(), "Server response code was 200 ");

    }
}
