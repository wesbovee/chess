package chess;

import java.util.Collection;

public class Game implements ChessGame{
    @Override
    public TeamColor getTeamTurn() {
        return null;
    }

    @Override
    public void setTeamTurn(TeamColor team) {

    }

    @Override
    public Collection<Move> validMoves(Position startPosition) {
        return null;
    }

    @Override
    public void makeMove(Move move) throws InvalidMoveException {

    }

    @Override
    public boolean isInCheck(TeamColor teamColor) {
        return false;
    }

    @Override
    public boolean isInCheckmate(TeamColor teamColor) {
        return false;
    }

    @Override
    public boolean isInStalemate(TeamColor teamColor) {
        return false;
    }

    @Override
    public void setBoard(Board board) {

    }

    @Override
    public Board getBoard() {
        return null;
    }
}
