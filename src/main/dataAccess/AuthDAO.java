package dataAccess;
import Server.ChessServer;
import ServerModels.AuthtokenModel;

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
        if(username != null){
            String create_statement = "INSERT INTO auths (token, username) VALUES (\"" + uuidAsString + "\", \""+ username+"\");";
            try{
                new Call().accessDB(create_statement, ChessServer.chessdb);
                return uuidAsString;
            } catch(DataAccessException e){
                throw new DataAccessException(e.getMessage());
            }
        }else{
            throw new DataAccessException("Error: bad request" );
        }
    }

    /**
     * function will return a boolean to verify the authorization
     * @param authtoken
     * @throws DataAccessException
     */
    public boolean exists(String authtoken) throws DataAccessException{
        String exist_statement = "SELECT * from auths WHERE token = '"+authtoken+"';";
        try {
            String auth = null;
            String username = null;
            ResultSet rs= new Call().fromDB(exist_statement, ChessServer.chessdb);
            while(rs.next()){
                auth = rs.getString("token");
                username = rs.getString("username");
            }
            return auth != null && username != null;
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
    public AuthtokenModel find(String authtoken) throws DataAccessException{
        try {
            String token = null;
            String username = null;
            String find_statement = "SELECT * from auths WHERE token = '"+authtoken+"';";
            ResultSet rs = new Call().fromDB(find_statement, ChessServer.chessdb);

            while(rs.next()) {
                token = rs.getString("token");
                username = rs.getString("username");
            }
            return new AuthtokenModel(token,username);
        } catch (SQLException e){
            throw new DataAccessException("Error: bad request" );
        }
    }

}
