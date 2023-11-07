package dataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Call {

    public void configureDatabase(Database db) throws SQLException {
        try (var conn = db.getConnection()) {
            var createDbStatement = conn.prepareStatement("CREATE DATABASE IF NOT EXISTS chess");
            createDbStatement.executeUpdate();

            conn.setCatalog("chess");

            var createGamesTable = """
            CREATE TABLE  IF NOT EXISTS games (
                ID INT NOT NULL AUTO_INCREMENT,
                black_username VARCHAR(255),
                white_username VARCHAR(255),
                name VARCHAR(255) NOT NULL,
                game TEXT NOT NULL,
                PRIMARY KEY (id)
            )""";

            var createUsersTable = """
            CREATE TABLE  IF NOT EXISTS users (
                username VARCHAR(255) NOT NULL,
                password VARCHAR(255) NOT NULL,
                email VARCHAR(255) NOT NULL UNIQUE,
                PRIMARY KEY (username)
            )""";

            var createAuthsTable = """
            CREATE TABLE  IF NOT EXISTS auths (
                token VARCHAR(255) NOT NULL,
                username VARCHAR(255) NOT NULL,
                PRIMARY KEY (token)
            )""";


            try (var createTableStatement = conn.prepareStatement(createGamesTable)) {
                createTableStatement.executeUpdate();
            }
            try (var createTableStatement = conn.prepareStatement(createUsersTable)) {
                createTableStatement.executeUpdate();
            }
            try (var createTableStatement = conn.prepareStatement(createAuthsTable)) {
                createTableStatement.executeUpdate();
            }
        } catch (DataAccessException e) {
            throw new SQLException(e);
        }
    }
    public boolean accessDB (String statement, Database db) throws DataAccessException{
        var conn = db.getConnection();

        try {
            var preparedStatement = conn.prepareStatement(statement);
            return preparedStatement.execute();
        } catch (SQLException ex) {
            throw new DataAccessException(ex.toString());
        }finally {
            db.returnConnection(conn);
        }
    }
    public ResultSet fromDB (String statement, Database db) throws DataAccessException{
        var conn = db.getConnection();

        try {
            var preparedStatement = conn.prepareStatement(statement);
            return preparedStatement.executeQuery();
        } catch (SQLException ex) {
            throw new DataAccessException(ex.toString());
        }finally {
            db.returnConnection(conn);
        }
    }

}
