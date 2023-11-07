package dataAccess;

import Server.ChessServer;
import ServerModels.UserModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;

public class UserDAO {
    public static HashMap<String, UserModel> users = new HashMap<>();
    public static HashSet<String> emails = new HashSet<>();
    /**
     * the clear method clears all users from the DB
     */
    public void clear() throws DataAccessException{
        String clear_statement = "TRUNCATE TABLE users;";
        new Call().accessDB(clear_statement, new Database());
    }

    /**
     * registers the user and adds info into the DB
     * @param user
     * @throws DataAccessException
     */
    public void create(UserModel user) throws DataAccessException {
        String email = user.getEmail();
        String username = user.getUsername();
        try {
            String create_statement = "INSERT INTO users (username, password, email) VALUES ('" + username + "', '" + user.getPassword() + "', '" + email + "');";
            new Call().accessDB(create_statement, ChessServer.chessdb);
        }catch (DataAccessException e){
            throw new DataAccessException(e.getMessage());
        }
    }

    /**
     * this function aids in logging in by
     * 1. finding the account being referred
     * 2. returning said user
     * @param username
     * @return UserModel
     * @throws DataAccessException
     */
    public UserModel find(String username) throws DataAccessException{
        String un = null;
        String pw = null;
        String em = null;
        String find_statement = "SELECT * from users WHERE username = '"+username+"';";
        ResultSet rs = new Call().fromDB(find_statement, ChessServer.chessdb);
        try {
            while(rs.next()) {
                un = rs.getString("username");
                pw = rs.getString("password");
                em = rs.getString("email");
            }
            return new UserModel(un,pw,em);
        } catch (SQLException e){
            throw new DataAccessException("Error: bad request" );
        }
    }
}
