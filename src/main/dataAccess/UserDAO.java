package dataAccess;

import Server.ChessServer;
import ServerModels.UserModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

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
        if (exists(user)){
            throw new DataAccessException("Error: already taken" );
        }else {
            try {
                String create_statement = "INSERT INTO users (username, password, email) VALUES (\"" + username + "\", \"" + user.getPassword() + "\", \"" + email + "\");";
                new Call().accessDB(create_statement, ChessServer.chessdb);

            } catch (DataAccessException e) {
                throw new DataAccessException(e.getMessage());
            }
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

    public Boolean exists(UserModel user){
        String un = user.getUsername();
        String em = user.getEmail();
        String returnedUN = null;
        String returnedEM = null;
        Boolean exist = false;
        try {
            String exist_un_statement = "SELECT username from users WHERE username = '" + un + "';";
            ResultSet rs = new Call().fromDB(exist_un_statement, ChessServer.chessdb);
            while (rs.next()) {
                returnedUN = rs.getString("username");
                if (Objects.equals(un, returnedUN)) {
                    exist = true;
                }
            }
        }catch (DataAccessException | SQLException e){
            try{
                String exist_em_statement = "SELECT email from users WHERE username = '" + em + "';";
                ResultSet rs = new Call().fromDB(exist_em_statement, ChessServer.chessdb);
                while (rs.next()) {
                    returnedEM = rs.getString("email");
                    if (Objects.equals(em, returnedEM)) {
                        exist = true;
                    }
                }
            }catch (DataAccessException | SQLException f ) {
                exist = false;
            }
        }
        return exist;
    }
}
