package MyPhase4Tests;

import Server.ChessServer;
import ServerModels.GameModel;
import chess.ChessGame;
import chess.Game;
import com.google.gson.Gson;
import dataAccess.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class GameDAOTest {
    public int existingID;
    public String existingBUN = "testBlack";
    public String existingWUN = "testWhite";
    public String existingGN = "testGame";
    public ChessGame existingGame = new Game();

    public int newID;
    public String newBUN = "newTestBUN";
    public String newWUN = "newTestWUN";
    public String newGN = "newTestGN";
    public ChessGame newGame = new Game();

    @BeforeEach
    void setUp() throws SQLException, DataAccessException {
        new GameDAO().clear();

        var conn = ChessServer.chessdb.getConnection();
        var createDbStatement = conn.prepareStatement("CREATE DATABASE IF NOT EXISTS chess");
        createDbStatement.executeUpdate();

        conn.setCatalog("chess");

        var createGamesTable = """
            CREATE TABLE  IF NOT EXISTS games (
                ID INT NOT NULL AUTO_INCREMENT,
                black_username VARCHAR(255),
                white_username VARCHAR(255),
                name VARCHAR(255) NOT NULL,
                game TEXT,
                PRIMARY KEY (id)
            )""";

        var createGamesEntry = "INSERT INTO games (black_username, white_username, name, game) VALUES (\""+existingBUN+"\", \""+existingWUN+"\", \""+existingGN+"\","+new Gson().toJson(existingGame)+");";

        var createTableStatement = conn.prepareStatement(createGamesTable);
        createTableStatement.execute();
        var createEntryStatement = conn.prepareStatement("INSERT INTO games (black_username, white_username, name, game) VALUES (?, ?, ?,?)");
        createEntryStatement.setString(1,existingBUN);
        createEntryStatement.setString(2,existingWUN);
        createEntryStatement.setString(3,existingGN);
        createEntryStatement.setString(4,new Gson().toJson(existingGame));
        createEntryStatement.execute();
        String get_id = "SELECT ID from games WHERE name = \""+existingGN+"\";";
        ResultSet rs = new Call().fromDB(get_id,ChessServer.chessdb);
        while (rs.next()){
            existingID = rs.getInt("ID");
        }
    }

    @AfterEach
    void tearDown() throws DataAccessException {
        new GameDAO().clear();
    }

    @Test
    void clear() throws SQLException, DataAccessException {
        int testid = -1234567890;
        String testBUN = null;
        String testWUN = null;
        String testGN = null;
        ChessGame testGame = null;

        new GameDAO().clear();
        String get_id = "SELECT * from games WHERE name = \""+existingGN+"\";";
        ResultSet rs = new Call().fromDB(get_id,ChessServer.chessdb);
        while (rs.next()){
            testid = rs.getInt("ID");
            testBUN = rs.getString("black_username");
            testWUN = rs.getString("white_username");
            testGN = rs.getString("name");
            testGame = new GameDAO().deserialize(rs.getString("game"));
        }
        assertNull(testBUN,"Black username not cleared from DB");
        assertNull(testWUN,"White username not cleared from DB");
        assertNull(testGN,"Game name not cleared from DB");
        assertNull(testGame, "Game not cleared from DB");
        assertEquals(-1234567890,testid, "ID not cleared from DB");
    }

    @Test
    void listOGamesPositive() throws DataAccessException, SQLException {
        var conn = ChessServer.chessdb.getConnection();
        var createEntryStatement = conn.prepareStatement("INSERT INTO games (black_username, white_username, name, game) VALUES (?, ?, ?,?)");
        createEntryStatement.setString(1,newBUN);
        createEntryStatement.setString(2,newWUN);
        createEntryStatement.setString(3,newGN);
        createEntryStatement.setString(4,new Gson().toJson(newGame));
        createEntryStatement.execute();
        String get_id = "SELECT ID from games WHERE name = \""+newGN+"\";";
        ResultSet rs = new Call().fromDB(get_id,ChessServer.chessdb);
        while (rs.next()){
            newID = rs.getInt("ID");
        }

        Collection<GameModel> games = new GameDAO().listOGames();

        assertEquals(2,games.size(), "does not return all games");

    }
    @Test
    void listOGamesNegative() {
        try {
            new GameDAO().clear();
            Collection<GameModel> games = new GameDAO().listOGames();
        } catch (DataAccessException e) {
            assertEquals("Error: bad request",e.getMessage(),"does not throw error when games is empty");
        }
    }

    @Test
    void createPositive() throws DataAccessException, SQLException {
        String returnedName = null;
        ChessGame returnedGame = null;
        int nid = new GameDAO().create(newGN);
        String get_id = "SELECT * from games WHERE name = \""+newGN+"\";";
        ResultSet rs = new Call().fromDB(get_id,ChessServer.chessdb);
        while (rs.next()){
            newID = rs.getInt("ID");
            returnedName = rs.getString("name");
            returnedGame = new GameDAO().deserialize(rs.getString("game"));
        }
        assertEquals(nid,newID,"Id should be 2");
        assertEquals(newGN,returnedName,"game name returned does not match");
        if (returnedGame != null){
            assertEquals(Game.class, returnedGame.getClass(),"does not return a game object");
        }
    }
    @Test
    void createNegative() {
        try{
            int nid = new GameDAO().create("");
        }catch (DataAccessException e){
            assertEquals("Error: bad request",e.getMessage(), "does not throw error");
            return;
        }
        fail();
    }
    @Test
    void existsPositive() {
        Boolean exists = new GameDAO().exists(existingID);
        assertEquals(true, exists,"returns false when true");
    }
    @Test
    void existsNegative(){
        Boolean exists = new GameDAO().exists(-49);
        assertEquals(false, exists,"returns false when true");
    }

    @Test
    void claimSpotPositive() throws DataAccessException, SQLException {
        String returnedName = null;
        String returnedBUN = null;
        int returnedID = -12;
        newID = new GameDAO().create(newGN);
        new GameDAO().claimSpot(newBUN, ChessGame.TeamColor.BLACK,newID);
        String get_id = "SELECT * from games WHERE name = \""+newGN+"\";";
        ResultSet rs = new Call().fromDB(get_id,ChessServer.chessdb);
        while (rs.next()){
            returnedID = rs.getInt("ID");
            returnedName = rs.getString("name");
            returnedBUN = rs.getString("black_username");
        }
        assertEquals(newBUN,returnedBUN,"new username not returned");
        assertEquals(newGN,returnedName,"new game name not returned");
        assertEquals(newID,returnedID,"new ID not returned");
    }
    @Test
    void claimSpotNegative() {
        try {
            new GameDAO().claimSpot(newBUN, ChessGame.TeamColor.BLACK, existingID);
        }catch (DataAccessException e) {
            assertEquals("Error: already taken",e.getMessage(), "Error not thrown");

        }
    }

    @Test
    void deletePositive() {
        try {
            new GameDAO().delete(existingID);
            String get_id = "SELECT * from games where ID = "+existingID+";";
        }catch(DataAccessException e){
            assertEquals("Error: bad request",e.getMessage(),"does not throw error");
        }
    }
    @Test
    void deleteNegative() throws SQLException, DataAccessException {
        int returnedID  = 1234567890;
        String returnedName = null;
        String returnedBUN = null ;
        String returnedWUN = null;
        ChessGame returnedGame = null;
        try {
            new GameDAO().delete(2);
        }catch(DataAccessException e){
            String get_id = "SELECT * from games where ID = "+existingID+";";
            ResultSet rs = new Call().fromDB(get_id,ChessServer.chessdb);
            while (rs.next()){
                returnedID = rs.getInt("ID");
                returnedName = rs.getString("name");
                returnedBUN = rs.getString("black_username");
                returnedWUN = rs.getString("white_username");
                returnedGame = new GameDAO().deserialize(rs.getString("game"));
            }
            assertEquals(existingBUN,returnedBUN,"black username still in database");
            assertEquals(existingGN,returnedName,"game name still in database");
            assertEquals(existingID,returnedID,"ID still in database");
            assertEquals(existingWUN,returnedWUN,"White username is still in database");
            assertEquals(Game.class,returnedGame.getClass(),"Game is still in DB");
        }
    }
}