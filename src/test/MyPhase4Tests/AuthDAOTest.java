package MyPhase4Tests;

import Server.ChessServer;
import ServerModels.AuthtokenModel;
import dataAccess.AuthDAO;
import dataAccess.Call;
import dataAccess.DataAccessException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class AuthDAOTest {
    public String existingToken =  "testToken";
    public String existingUser = "testUser";

    public String newToken =null;
    public String newUser = "itsame";

    @BeforeEach
    void setUp() throws SQLException, DataAccessException {
        new AuthDAO().clear();

        var conn = ChessServer.chessdb.getConnection();
        var createDbStatement = conn.prepareStatement("CREATE DATABASE IF NOT EXISTS chess");
        createDbStatement.executeUpdate();

        conn.setCatalog("chess");

        var createAuthTable = """
        CREATE TABLE  IF NOT EXISTS auths (
            token VARCHAR(255) NOT NULL,
            username VARCHAR(255) NOT NULL,
            PRIMARY KEY (token)
        )""";

        var createAuthEntry = "INSERT INTO auths (token, username) VALUES (\""+existingToken+"\", \""+existingUser+"\");";

        var createTableStatement = conn.prepareStatement(createAuthTable);
        createTableStatement.execute();
        var createEntryStatement = conn.prepareStatement(createAuthEntry);
        createEntryStatement.execute();

    }

    @AfterEach
    void tearDown() throws DataAccessException {
        new AuthDAO().clear();
    }

    @Test
    void clear() throws DataAccessException,SQLException{
        new AuthDAO().clear();
        String auth = null;
        String username = null;
        String checkClear = "SELECT * FROM auths;";
        ResultSet rs = null;
        rs = new Call().fromDB(checkClear, ChessServer.chessdb);
        while(rs.next()){
            auth = rs.getString("token");
            username = rs.getString("username");

        }
        assertNull(auth, "database contains auth tokens");
        assertNull(username, "database contains usernames");
    }

    @Test
    void deletePositive() throws DataAccessException,SQLException {
        new AuthDAO().delete(existingToken);
        String auth = null;
        String username = null;
        String checkClear = "SELECT * FROM auths Where username = \""+existingUser+"\";";
        ResultSet rs = new Call().fromDB(checkClear, ChessServer.chessdb);
        while(rs.next()){
            auth = rs.getString("token");
            username = rs.getString("username");

        }
        assertNull(auth, "database still contains existing auth token");
        assertNull(username, "database still contains existing username");
    }
    @Test
    void deleteNegative() throws DataAccessException,SQLException{
        new AuthDAO().delete(existingUser);
        String auth = null;
        String username = null;
        String checkClear = "SELECT * FROM auths Where username = \""+existingUser+"\";";
        ResultSet rs = new Call().fromDB(checkClear, ChessServer.chessdb);
        while(rs.next()){
            auth = rs.getString("token");
            username = rs.getString("username");
        }
        assertEquals(existingToken, auth , "database does not contain existing auth token");
        assertEquals(existingUser, username , "database does not contain existing username");
    }

    @Test
    void createPositive() throws DataAccessException,SQLException{
        newToken= new AuthDAO().create(newUser);
        String auth = null;
        String username = null;
        String checkClear = "SELECT * FROM auths Where token = \""+newToken+"\";";
        ResultSet rs = new Call().fromDB(checkClear, ChessServer.chessdb);
        while(rs.next()){
            auth = rs.getString("token");
            username = rs.getString("username");
        }
        assertEquals(newToken, auth , "database does not contain new auth token");
        assertEquals(newUser, username , "database does not contain new username");
    }
    @Test
    void createNegative(){
        try{
            newToken= new AuthDAO().create(null);
            String auth = null;
            String username = null;
            String checkClear = "SELECT * FROM auths Where token = \""+newToken+"\";";
            ResultSet rs = new Call().fromDB(checkClear, ChessServer.chessdb);
            while(rs.next()){
                auth = rs.getString("token");
                username = rs.getString("username");
            }
        } catch (DataAccessException | SQLException e) {
            assertEquals("Error: bad request", e.getMessage(), "Does not throw error");
        }
    }
    @Test
    void existsPositive() throws DataAccessException{
        boolean exists =new AuthDAO().exists(existingToken);
        assertEquals(true, exists, "could not find existing token");
    }
    @Test
    void existsNegative() throws DataAccessException{
        boolean exists =new AuthDAO().exists("existingToken");
        assertEquals(false, exists, "found a token that does not exist");
    }

    @Test
    void findPositive() throws DataAccessException{
        AuthtokenModel authObj = new AuthDAO().find(existingToken);
        String auth = authObj.getAuthToken();
        String username =  authObj.getUsername();

        assertEquals(existingToken, auth , "database does not contain existing auth token");
        assertEquals(existingUser, username , "database does not contain existing username");
    }
    @Test
    void findNegative() {
        try{
            AuthtokenModel authObj = new AuthDAO().find("existingToken");

        } catch (DataAccessException e) {
            assertEquals("Error: bad request", e.getMessage() , "does not throw error");
        }
    }
}