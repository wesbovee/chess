package dataAccess;
import Server.ChessServer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class AuthDAO {
    public static HashMap<String,String> auths = new HashMap<>();
    /**
     * the clear method clears all authorization tokens from the DB
     */
    public void clear() throws DataAccessException{
        String clear_statement = "TRUNCATE TABLE auths;";
        new Call().accessDB(clear_statement, ChessServer.chessdb);
    }

    /**
     * uses the auth token to delete the authtoken from the database to end the session and log out the user
     * @param authtoken
     * @throws DataAccessException
     */
    public void delete(String authtoken) throws DataAccessException {
        String delete_statement = "DELETE from auths WHERE token = '"+authtoken+"';";
        new Call().accessDB(delete_statement, ChessServer.chessdb);
    }

    /**
     * creates an authorization token and stores it in the database
     * @param username
     * @throws DataAccessException
     */
    public String create(String username) throws DataAccessException {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        String create_statement = "INSERT INTO auths (token, username) VALUES ('" + uuidAsString + "', '"+ username+"');";
        try{
            new Call().accessDB(create_statement, ChessServer.chessdb);
            return uuidAsString;
        } catch(DataAccessException e){
            throw new DataAccessException(e.getMessage());
        }
    }

    /**
     * function will return a boolean to verify the authorization
     * @param authtoken
     * @throws DataAccessException
     */
    public boolean exists(String authtoken) throws DataAccessException{
        String exist_statement = "SELECT token from auths WHERE token = '"+authtoken+"';";
        if (new Call().accessDB(exist_statement, ChessServer.chessdb)){
            return true;
        }
        else{
            throw new DataAccessException("Error: unauthorized" );
        }
    }
    public String find(String authtoken) throws DataAccessException{
        String token = null;
        String find_statement = "SELECT token from auths WHERE token = '"+authtoken+"';";
        ResultSet rs = new Call().fromDB(find_statement, ChessServer.chessdb);
        try {
            while(rs.next()) {
                token = rs.getString("token");
            }
            return token;
        } catch (SQLException e){
            throw new DataAccessException("Error: bad request" );
        }
    }

}
