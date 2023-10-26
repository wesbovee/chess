package dataAccess;

import ServerModels.GameModel;
import ServerModels.UserModel;
import chess.ChessGame;
import chess.Game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class GameDAO {
    public static HashMap<Integer, GameModel> g_list;
    public static int count = 0;
    /**
     * the clear method clears all games from the DB
     */
    public void clear() throws DataAccessException{
        if (true){
            count = 0;
            g_list.clear();
        }
        else{
            throw new DataAccessException("Error: description");
        }
    }

    /**
     * this function will return a list of all gameModel objects in the data base.
     * @return Collection of GameModels
     * @throws DataAccessException
     */
    public Collection<GameModel> listOGames() throws DataAccessException{
        if(true){
            return g_list.values();
        }
        else{
            throw new DataAccessException("Error: description");
        }
    }

    /**
     * creates a new object of type GameModel and stores it in the database
     * @return GameModel
     * @throws DataAccessException
     */
    public int create(String gameName) throws DataAccessException{
        count++;
        GameModel game = new GameModel(count, null, null, gameName, new Game());
        g_list.put(count,game);
        return count;
    }
    /**
     * find function aids in the join game function this is step one to ensure that the game exisits
     * @param id
     * @return GameModel
     * @throws DataAccessException
     */
    public boolean exists(int id) throws DataAccessException {
        if (g_list.containsKey(id)) {
            return true;
        }else{
            throw new DataAccessException("Error: bad request");
        }
    }

    /**
     * replaces a current game model in the database with the provided game model
     * @param g
     * @throws DataAccessException
     */
    public void update(GameModel g, int gameID) throws DataAccessException {
        g_list.remove(gameID);
        g_list.put(gameID,g);
    }

    /**
     * uses username and color to join a game and reserve a team
     * @param un
     * @param col
     * @throws DataAccessException
     */
    public void claimSpot(String un, String col, int gameID) throws DataAccessException{
        GameModel currentGame = g_list.get(gameID);
        if (col == "WHITE"){
            if (currentGame.getWhiteUsername() != null) {
                currentGame.setWhiteUsername(un);
            }else{
                throw new DataAccessException("Error: already taken");
            }
        }else if (col == "BLACK"){
            if(currentGame.getBlackUsername() != null){
                currentGame.setBlackUsername(un);
            }else{
                throw new DataAccessException("Error: already taken");
            }
        }else {
            throw new DataAccessException("Error: bad request");
        }
        update(currentGame,gameID);
    }

    /**
     * deletes the individual game
     * @param gID
     * @throws DataAccessException
     */
    public void delete(int gID) throws DataAccessException {
    }

}
