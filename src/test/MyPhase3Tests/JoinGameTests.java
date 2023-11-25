package MyPhase3Tests;

import chess.ChessGame;
import org.junit.jupiter.api.*;
import passoffTests.TestFactory;
import passoffTests.obfuscatedTestClasses.TestServerFacade;
import passoffTests.testClasses.TestModels;

public class JoinGameTests {
    private static final int HTTP_OK = 200;
    private static final int HTTP_BAD_REQUEST = 400;
    private static final int HTTP_UNAUTHORIZED = 401;
    private static final int HTTP_FORBIDDEN = 403;

    private static TestModels.TestUser existingUser;
    private static TestModels.TestUser newUser;
    private static TestModels.TestCreateRequest createRequest;
    private static TestServerFacade serverFacade;
    private String existingAuth;
    private int createdID = 0;


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
    @DisplayName("Positive Join Games test")
    void testJoinGamesPositive(){
        TestModels.TestJoinRequest request = new TestModels.TestJoinRequest();
        request.gameID = createdID;
        request.playerColor = ChessGame.TeamColor.WHITE;

        TestModels.TestResult White = serverFacade.verifyJoinPlayer(request,existingAuth);
        Assertions.assertEquals(HTTP_OK, serverFacade.getStatusCode(), "Server response code was not 200 OK");

        TestModels.TestWatchRequest watch = new TestModels.TestWatchRequest();
        watch.gameID = createdID;
        TestModels.TestResult observer = serverFacade.verifyJoinObserver(watch,existingAuth);
        Assertions.assertEquals(HTTP_OK, serverFacade.getStatusCode(), "Server response code was not 200 OK");

    }

    @Test
    @DisplayName("Negative Join Games test")
    void testJoinGamesNegative(){
        TestModels.TestJoinRequest request = new TestModels.TestJoinRequest();
        request.gameID = createdID;
        request.playerColor = ChessGame.TeamColor.WHITE;

        TestModels.TestResult White = serverFacade.verifyJoinPlayer(request,"existingAuth");
        Assertions.assertNotEquals(HTTP_OK, serverFacade.getStatusCode(), "Server response code was 200 ");
        TestModels.TestJoinRequest request2 = new TestModels.TestJoinRequest();
        request.gameID = createdID;
        request.playerColor = ChessGame.TeamColor.BLACK;
        TestModels.TestResult Black = serverFacade.verifyJoinPlayer(request2,"existingAuth");
        Assertions.assertNotEquals(HTTP_OK, serverFacade.getStatusCode(), "Server response code was 200 ");
        TestModels.TestWatchRequest watch = new TestModels.TestWatchRequest();
        watch.gameID = createdID;
        TestModels.TestResult observer = serverFacade.verifyJoinObserver(watch,"existingAuth");
        Assertions.assertNotEquals(HTTP_OK, serverFacade.getStatusCode(), "Server response code was 200 ");

    }
}
