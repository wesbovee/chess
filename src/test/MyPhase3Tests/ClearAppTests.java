package MyPhase3Tests;

import chess.ChessGame;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import passoffTests.TestFactory;
import passoffTests.obfuscatedTestClasses.TestServerFacade;
import passoffTests.testClasses.TestModels;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClearAppTests {
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
    void testClearApplication(){
        createRequest.gameName = "Rick";
        serverFacade.createGame(createRequest, existingAuth);

        createRequest.gameName = "Great game";
        serverFacade.createGame(createRequest, existingAuth);

        //log in new user
        TestModels.TestRegisterRequest registerRequest = new TestModels.TestRegisterRequest();
        registerRequest.username = "BrotherSB";
        registerRequest.password = "pantssquared";
        registerRequest.email = "pineapple@under.sea";
        TestModels.TestLoginRegisterResult registerResult = serverFacade.register(registerRequest);

        //create and join game for new user
        createRequest.gameName = "Pat";
        TestModels.TestCreateResult createResult = serverFacade.createGame(createRequest, registerResult.authToken);

        TestModels.TestJoinRequest joinRequest = new TestModels.TestJoinRequest();
        joinRequest.gameID = createResult.gameID;
        joinRequest.playerColor = ChessGame.TeamColor.WHITE;
        serverFacade.verifyJoinPlayer(joinRequest, registerResult.authToken);

        //do clear
        TestModels.TestResult clearResult = serverFacade.clear();

        //check hashmaps
        try {
            new AuthDAO();
            assertTrue(AuthDAO.auths.isEmpty(), "Auths hashmap is not empty");
            new GameDAO();
            assertTrue(GameDAO.g_list.isEmpty(), "g_list hashmap is not empty");
            new UserDAO();
            assertTrue(UserDAO.users.isEmpty(), "User hashmap is not empty");
        }catch (AssertionError a){
            System.out.println(a.getMessage());
        }
    }
}
