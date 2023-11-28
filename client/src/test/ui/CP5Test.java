package ui;

import Responses.*;
import chess.ChessGame;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class CP5Test {

    public static ServerFacade testServer = new ServerFacade();
    public static String existingUN = "wes";
    public static String existingPW = "wesp";
    public static String existingEM = "wese";
    public static String newUN = "nwes";
    public static String newPW = "nwesp";
    public static String newEM = "nwese";

    @BeforeAll
    //truncate users;
    public static void setup() throws Exception {
        String token = testServer.pli_register(existingUN,existingPW,existingEM).getAuthToken();
        testServer.logout(token);
    }
    @Test
    @Order(1)
    void pli_help_positive() {
        String helpString = testServer.pli_help();
        String expectedString = "\u001b[36;49;1m   register <USERNAME> <PASSWORD> <EMAIL>" +"\u001b[39;49;0m - to create an account\n" +
                "\u001b[36;49;1m   login <USERNAME> <PASSWORD>"+"\u001b[39;49;0m - to play chess\n" +
                "\u001b[36;49;1m   quit" + "\u001b[39;49;0m - playing chess\n" +
                "\u001b[36;49;1m   help" + "\u001b[39;49;0m - with possible commands\n";
        assertEquals(expectedString,helpString, "Did not return propper string for prelogin help.");
    }

    @Test
    @Order(2)
    void pli_help_negative() {
        String helpString = testServer.pli_help();
        String expectedString = "\u001b[36;49;1m   create <NAME>" +"\u001b[39;49;0m - a game\n" +
                "\u001b[36;49;1m   list"+"\u001b[39;49;0m - games\n" +
                "\u001b[36;49;1m   join <ID> [WHITE|BLACK|<empty>]"+"\u001b[39;49;0m - a game\n" +
                "\u001b[36;49;1m   observe <ID>"+"\u001b[39;49;0m - a game\n" +
                "\u001b[36;49;1m   logout"+"\u001b[39;49;0m - when you are done\n" +
                "\u001b[36;49;1m   quit" + "\u001b[39;49;0m - playing chess\n" +
                "\u001b[36;49;1m   help" + "\u001b[39;49;0m - with possible commands\n";
        assertNotEquals(expectedString,helpString, "returns post login help when calling pre login help.");
    }

    @Test
    @Order(3)
    void quit_positive() {
        assertFalse(testServer.quit(), "Does not end while loop to quit program.");
    }

    @Test
    @Order(4)
    void pli_login_positive() throws Exception {
        LoginResponse login= testServer.pli_login(existingUN,existingPW);
        assertNull(login.getMessage(),"Valid login threw error.");
        assertNotNull(login.getAuthToken(),"Valid login did not return token");
        testServer.logout(login.getAuthToken());
    }

    @Test
    @Order(5)
    void pli_login_negative() throws Exception {
        LoginResponse login= testServer.pli_login(newUN,newPW);
        assertEquals("Error: unauthorized",login.getMessage(),"Invalid login did not throw error.");
        assertNull(login.getAuthToken(),"Invalid login returns token");
    }
    @Test
    @Order(6)
    void pli_register_positive() throws Exception {
        RegisterResponse register= testServer.pli_register(newUN,newPW,newEM);
        assertNull(register.getMessage(),"Valid registration threw error.");
        assertNotNull(register.getAuthToken(),"Valid registration did not return token");
        testServer.logout(register.getAuthToken());
    }
    @Test
    @Order(7)
    void pli_register_negative() throws Exception {
        RegisterResponse register= testServer.pli_register(existingUN,existingPW,existingEM);
        assertEquals("Error: already taken",register.getMessage(),"Invalid registration did not throw error.");
        assertNull(register.getAuthToken(),"Invalid registration returns token");
    }

    @Test
    @Order(8)
    void help_positive() {
        String helpString = testServer.help();
        String expectedString = "\u001b[36;49;1m   create <NAME>" +"\u001b[39;49;0m - a game\n" +
                "\u001b[36;49;1m   list"+"\u001b[39;49;0m - games\n" +
                "\u001b[36;49;1m   join <ID> [WHITE|BLACK|<empty>]"+"\u001b[39;49;0m - a game\n" +
                "\u001b[36;49;1m   observe <ID>"+"\u001b[39;49;0m - a game\n" +
                "\u001b[36;49;1m   logout"+"\u001b[39;49;0m - when you are done\n" +
                "\u001b[36;49;1m   quit" + "\u001b[39;49;0m - playing chess\n" +
                "\u001b[36;49;1m   help" + "\u001b[39;49;0m - with possible commands\n";
        assertEquals(expectedString,helpString, "Did not return propper string for prelogin help.");
    }

    @Test
    @Order(9)
    void help_negative() {
        String helpString = testServer.help();
        String expectedString = "\u001b[36;49;1m   register <USERNAME> <PASSWORD> <EMAIL>" +"\u001b[39;49;0m - to create an account\n" +
                "\u001b[36;49;1m   login <USERNAME> <PASSWORD>"+"\u001b[39;49;0m - to play chess\n" +
                "\u001b[36;49;1m   quit" + "\u001b[39;49;0m - playing chess\n" +
                "\u001b[36;49;1m   help" + "\u001b[39;49;0m - with possible commands\n";
        assertNotEquals(expectedString,helpString, "Returns pre login help when calling post login help.");
    }
    @Test
    @Order(10)
    void logout_positive() throws Exception {
        String token = testServer.pli_login(existingUN,existingPW).getAuthToken();
        LogoutResponse logout = testServer.logout(token);
        assertNull(logout.getMessage(),"Valid logout returns error");
    }
    @Test
    @Order(11)
    void logout_negative() throws Exception {
        String token = "nada";
        LogoutResponse logout = testServer.logout(token);
        assertEquals("Error: unauthorized",logout.getMessage(),"Valid logout returns error");
    }
    @Test
    @Order(12)
    //must truncate games table before "truncate games;"
    void createGame_positive() throws Exception {
        String token = testServer.pli_login(existingUN,existingPW).getAuthToken();
        CreateGameResponse create = testServer.createGame("one",token);
        assertEquals(1,create.getGameID(),"Valid creation did not return ID");
        assertNull(create.getMessage(),"Valid Creation returns error");
        testServer.logout(token);
    }

    @Test
    @Order(13)
    void createGame_negative() throws Exception {
        String token = "nada";
        CreateGameResponse create = testServer.createGame("one",token);
        assertEquals(0,create.getGameID(),"Invalid creation returns ID");
        assertEquals("Error: unauthorized",create.getMessage(),"Invalid does not return error");
    }
    @Test
    @Order(14)
    void listGames_positive() throws Exception {
        String token = testServer.pli_login(existingUN,existingPW).getAuthToken();
        ListGamesResponse listgames = testServer.listGames(token);
        assertNull(listgames.getMessage(),"Valid list request returning errors");
        assertNotNull(listgames.getGames(), "Valid list request returned null instead of games");
    }

    @Test
    @Order(15)
    void listGames_negative() throws Exception {
        ListGamesResponse listgames = testServer.listGames("nada");
        assertEquals("Error: unauthorized",listgames.getMessage(),"Invalid list does not return error");
    }
    @Test
    @Order(16)
    void joinGame_positive() throws Exception {
        String token = testServer.pli_login(existingUN,existingPW).getAuthToken();
        JoinGameResponse play = testServer.joinGame("1", "WHITE",token);
        assertNull(play.getMessage(),"Valid join game returned error");
        testServer.logout(token);
    }

    @Test
    @Order(17)
    void joinGame_negative() throws Exception {
        JoinGameResponse play = testServer.joinGame("1", "WHITE","token");
        assertEquals("Error: unauthorized",play.getMessage(),"Invalid join does not return error");
    }
    @Test
    @Order(18)
    void joinObserver_positive() throws Exception {
        String token = testServer.pli_login(existingUN,existingPW).getAuthToken();
        JoinGameResponse observe = testServer.joinObserver("1",token);
        assertNull(observe.getMessage(),"Valid join game returned error");
        testServer.logout(token);
    }

    @Test
    @Order(19)
    void joinObserver_negative() throws Exception {
        JoinGameResponse play = testServer.joinObserver("1", "token");
        assertEquals("Error: unauthorized",play.getMessage(),"Invalid join does not return error");
    }

}