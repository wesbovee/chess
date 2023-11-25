package dataAccess;

import Server.ChessServer;
import ServerModels.GameModel;
import chess.*;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;

public class GameDAO {
    public static HashMap<Integer, GameModel> g_list = new HashMap<>();
    /**
     * the clear method clears all games from the DB
     */
    public void clear() throws DataAccessException{
        String clear_statement = "TRUNCATE TABLE games;";
        new Call().accessDB(clear_statement, ChessServer.chessdb);
    }

    /**
     * this function will return a list of all gameModel objects in the data base.
     * @return Collection of GameModels
     * @throws DataAccessException
     */
    public Collection<GameModel> listOGames() throws DataAccessException {
        g_list.clear();
        int gid = 0;
        String bun = null;
        String wun = null;
        String gname = null;
        ChessGame game = null;
        String find_statement = "SELECT * from games;";
        try(ResultSet rs = new Call().fromDB(find_statement, ChessServer.chessdb)) {
            while (rs.next()) {
                gid = rs.getInt("ID");
                bun = rs.getString("black_username");
                wun = rs.getString("white_username");
                gname = rs.getString("name");
                game = deserialize(rs.getString("game"));
                g_list.put(gid, new GameModel(gid, wun, bun, gname, game));
            }
            return g_list.values();
        } catch (SQLException e) {
            throw new DataAccessException("Error: bad request");
        }
    }
    /**
     * creates a new object of type GameModel and stores it in the database
     * @return int
     * @throws DataAccessException
     */
   public int create(String gameName) throws DataAccessException{
       try {
            String game = new Gson().toJson(new Game());
            int gid = 0;
            String create_statement = "INSERT INTO games (name) VALUES (\""+ gameName + "\");";
            new Call().accessDB(create_statement, ChessServer.chessdb);
            String get_id = "Select ID FROM games WHERE name = \""+gameName+"\";";
            try(ResultSet rs = new Call().fromDB(get_id,ChessServer.chessdb)) {
                while (rs.next()) {
                    gid = rs.getInt("ID");
                }
                new Call().updateGame(gid, game, ChessServer.chessdb);
                return gid;
            }
        } catch (SQLException e){
            throw new DataAccessException("Error: bad request" );
        }
    }
   /**
     * find function aids in the join game function this is step one to ensure that the game exisits
     * @param id
     * @return boolean
     * @throws DataAccessException
     */


   public boolean exists(int id){
       int returnedID = -12;
       Boolean ret = false;
        try {
            String exists_statement = "SELECT * from games WHERE ID = " + id + ";";
            try(ResultSet rs = new Call().fromDB(exists_statement, ChessServer.chessdb)) {
                while (rs.next()) {
                    returnedID = rs.getInt("ID");
                }
                if (returnedID != -12) {
                    ret = true;
                }
            }
        } catch (DataAccessException | SQLException e){
            return false;
        }
        return ret;
   }

    /**
     * uses username and color to join a game and reserve a team
     * @param un
     * @param col
     * @throws DataAccessException
     */
    public void claimSpot(String un, ChessGame.TeamColor col, int gameID) throws DataAccessException{
        String claim_statement = null;
        String check_statement = null;
        String wUN = null;
        String bUN = null;
        if (col.equals(ChessGame.TeamColor.WHITE)){
            check_statement = "SELECT white_username, black_username from games where ID ="+gameID+";";
            claim_statement = "Update games SET white_username = '" + un + "' WHERE ID = " +gameID+";";
        }else if (col.equals(ChessGame.TeamColor.BLACK)) {
            check_statement = "SELECT white_username, black_username from games where ID ="+gameID+";";
            claim_statement = "Update games SET black_username = '" + un + "' WHERE ID = " +gameID+";";
        }
        try(ResultSet rs = new Call().fromDB(check_statement,ChessServer.chessdb)){
            while (rs.next()){
                wUN = rs.getString("white_username");
                bUN = rs.getString("black_username");
            }
            if(col.equals(ChessGame.TeamColor.BLACK)&& bUN ==null || col.equals(ChessGame.TeamColor.WHITE) && wUN ==null ){
                new Call().accessDB(claim_statement, ChessServer.chessdb);
            }
            else{
                throw new DataAccessException("Error: already taken");
            }
        } catch (DataAccessException e){
            throw new DataAccessException("Error: already taken");
        } catch (SQLException e) {
            throw new DataAccessException("Error: bad request");
        }
    }

    /**
     * deletes the individual game
     * @param gID
     * @throws DataAccessException
     */
    public void delete(int gID) throws DataAccessException {
        try {
            String delete_statement = "Delete * from games where ID = "+gID+";";
            new Call().accessDB(delete_statement,ChessServer.chessdb);
        }catch (DataAccessException e){
            throw new DataAccessException("Error: bad request");
        }
    }

    public ChessGame deserialize (String game){
        var builder = new GsonBuilder();
        builder.registerTypeAdapter(ChessGame.class, new GameAdapter())
                .registerTypeAdapter(ChessBoard.class,new BoardAdapter())
                .registerTypeAdapter(ChessPiece.class,new PieceAdapter());
        return builder.create().fromJson(game, Game.class);
    }

    class GameAdapter implements JsonDeserializer<ChessGame> {
        public ChessGame deserialize(JsonElement el, Type type, JsonDeserializationContext ctx) throws JsonParseException {
            return new Gson().fromJson(el, Game.class);
        }
    }
    class BoardAdapter implements JsonDeserializer<ChessBoard> {
        public ChessBoard deserialize(JsonElement el, Type type, JsonDeserializationContext ctx) throws JsonParseException {
            return new Gson().fromJson(el, Board.class);
        }
    }
    class PieceAdapter implements JsonDeserializer<ChessPiece> {
        public ChessPiece deserialize(JsonElement el, Type type, JsonDeserializationContext ctx) throws JsonParseException {
            ChessPiece piece = null;
            String pieceType = el.getAsJsonObject().get("type").getAsString();
            switch (pieceType) {
                case "KING" -> piece = new Gson().fromJson(el, King.class);
                case "QUEEN" -> piece = new Gson().fromJson(el, Queen.class);
                case "BISHOP" -> piece = new Gson().fromJson(el, Bishop.class);
                case "KNIGHT" -> piece = new Gson().fromJson(el, Knight.class);
                case "ROOK" -> piece = new Gson().fromJson(el, Rook.class);
                case "PAWN" -> piece = new Gson().fromJson(el, Pawn.class);
            }
            return piece;
        }
    }
}
