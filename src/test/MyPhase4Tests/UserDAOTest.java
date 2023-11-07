package MyPhase4Tests;

import Server.ChessServer;
import ServerModels.UserModel;
import dataAccess.AuthDAO;
import dataAccess.Call;
import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {
    public String existingUsername = "testUsername";
    public String existingPassword = "testPassword";
    public String existingEmail = "test@e.mail";
    public String newUsername = "newUsername";
    public String newPassword = "newPassword";
    public String newEmail = "new@e.mail";


    @BeforeEach
    void setUp() throws SQLException, DataAccessException {
        new AuthDAO().clear();

        var conn = ChessServer.chessdb.getConnection();
        var createDbStatement = conn.prepareStatement("CREATE DATABASE IF NOT EXISTS chess");
        createDbStatement.executeUpdate();

        conn.setCatalog("chess");

        var createUsersTable = """
        CREATE TABLE  IF NOT EXISTS users (
            username VARCHAR(255) NOT NULL,
            password VARCHAR(255) NOT NULL,
            email VARCHAR(255) NOT NULL UNIQUE,
            PRIMARY KEY (username)
        )""";

        var createUsersEntry = "INSERT INTO users (username, password, email) VALUES (\""+existingUsername+"\", \""+existingPassword+"\",\""+existingEmail+"\");";

        var createTableStatement = conn.prepareStatement(createUsersTable);
        createTableStatement.execute();
        var createEntryStatement = conn.prepareStatement(createUsersEntry);
        createEntryStatement.execute();

    }

    @AfterEach
    void tearDown() throws DataAccessException {
        new UserDAO().clear();
    }

    @Test
    void clear() {
        try{
            new UserDAO().clear();
            String username = null;
            String password = null;
            String email = null;
            String checkClear = "SELECT * FROM users;";
            ResultSet rs = null;
            rs = new Call().fromDB(checkClear, ChessServer.chessdb);
            while(rs.next()){
                password = rs.getString("password");
                username = rs.getString("username");
                email = rs.getString("email");

            }
            assertNull(password, "database contains passwords");
            assertNull(username, "database contains usernames");
            assertNull(email, "database contains emails");
        } catch (DataAccessException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void createPositive() throws DataAccessException,SQLException {
        new UserDAO().create(new UserModel(newUsername,newPassword,newEmail));
        String username = null;
        String password = null;
        String email = null;
        String checkClear = "SELECT * FROM users WHERE username = \"" + newUsername+"\";";
        ResultSet rs = null;
        rs = new Call().fromDB(checkClear, ChessServer.chessdb);
        while(rs.next()){
            password = rs.getString("password");
            username = rs.getString("username");
            email = rs.getString("email");

        }
        assertEquals(newPassword,password, "database does not contain new password");
        assertEquals(newUsername, username, "database does not contain new username");
        assertEquals(newEmail, email, "database does not contain new email");
    }
    @Test
    void createNegative() {
        try {
            new UserDAO().create(new UserModel(existingUsername, existingPassword, existingEmail));
        }catch (DataAccessException e){
            assertEquals("Error: bad request", e.getMessage(),"Does not throw error with duplicate entry.");
        }
    }

    @Test
    void findPositive() throws DataAccessException {
        UserModel returnedUser = new UserDAO().find(existingUsername);
        assertEquals(existingUsername,returnedUser.getUsername(),"Username does not match");
        assertEquals(existingPassword, returnedUser.getPassword(),"Password does not match");
        assertEquals(existingEmail, returnedUser.getEmail(),"Email does not match");
    }
    @Test
    void findNegative() throws DataAccessException {
        try {
            UserModel returnedUser = new UserDAO().find("existingUsername");
        }catch (DataAccessException e){
            assertEquals("Error: bad request", e.getMessage(),"Does not throw error with duplicate entry.");
        }
    }

    @Test
    void existsPositive() {
        Boolean exists = new UserDAO().exists(new UserModel(existingUsername,existingPassword,existingEmail));
        assertEquals(true,exists,"returns false when it should return true");
    }
    @Test
    void existsNegative() {
        Boolean exists = new UserDAO().exists(new UserModel(newUsername,newPassword,newEmail));
        assertEquals(false,exists,"returns false when it should return true");
    }
}