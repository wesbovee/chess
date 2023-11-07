package MyPhase4Tests;

import dataAccess.DataAccessException;
import dataAccess.Database;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class AuthDAOTest {

    @BeforeEach
    void setUp() throws SQLException, DataAccessException {
        Database db = new Database();
        var conn = db.getConnection();
        var createDbStatement = conn.prepareStatement("CREATE DATABASE IF NOT EXISTS chess");
        createDbStatement.executeUpdate();

        conn.setCatalog("chess");

        var createAuthTable = """
        CREATE TABLE  IF NOT EXISTS auth (
            token VARCHAR(255) NOT NULL,
            username VARCHAR(255) NOT NULL,
            PRIMARY KEY (token)
        )""";

        var createAuthEntry = """
        INSERT INTO auth (token, username) VALUES ('" + uuidAsString + "', '"+ username+"');";
                        \s""";


        var createTableStatement = conn.prepareStatement(createAuthTable);
        createTableStatement.executeUpdate();

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void clear() {
    }

    @Test
    void delete() {
    }

    @Test
    void create() {
    }

    @Test
    void exists() {
    }

    @Test
    void find() {
    }
}