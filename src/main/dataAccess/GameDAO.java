package dataAccess;

import ServerModels.GameModel;
import ServerModels.UserModel;
import chess.ChessGame;

import java.util.Collection;

public class GameDAO {
    /**
     * the clear method clears all games from the DB
     */
    public void clear() throws DataAccessException{ }

    /**
     * this function will return a list of all gameModel objects in the data base.
     * @return Collection of GameModels
     * @throws DataAccessException
     */
    public Collection<GameModel> listOGames() throws DataAccessException{
        return null;
    }

    /**
     * creates a new object of type GameModel and stores it in the database
     * @return GameModel
     * @throws DataAccessException
     */
    public GameModel create() throws DataAccessException{
        return null;
    }
    /**
     * find function aids in the join game function this is step one to ensure that the game exisits
     * @param id
     * @return GameModel
     * @throws DataAccessException
     */
    public GameModel find(int id) throws DataAccessException {
        return null;
    }

    /**
     * replaces a current game model in the database with the provided game model
     * @param g
     * @throws DataAccessException
     */
    public void update(GameModel g) throws DataAccessException {
    }

    /**
     * deletes the individual game
     * @param gID
     * @throws DataAccessException
     */
    public void delete(int gID) throws DataAccessException {
    }

}
