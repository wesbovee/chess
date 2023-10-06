package chess;

import java.util.Collection;

import static chess.ChessGame.TeamColor.WHITE;

public class Game implements ChessGame{

    private TeamColor teamTurn;
    private Board gameBoard;
    public Game(){
        teamTurn = WHITE;
        gameBoard = new Board();

    }
    @Override
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    @Override
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    @Override
    public Collection<ChessMove> validMoves(Position startPosition) {
        if(gameBoard.getPiece(startPosition) == null){
            return null;
        }
        return gameBoard.getPiece(startPosition).pieceMoves(gameBoard, startPosition);
    }

    @Override
    public void makeMove(Move move) throws InvalidMoveException {
        Collection<ChessMove> valid = validMoves(move.getStartPosition());
        Board copy = gameBoard;
        if(valid.contains(move)){
            gameBoard.addPiece(move.getEndPosition(), gameBoard.getPiece(move.getStartPosition()));
            gameBoard.addPiece(move.getStartPosition(), null);
        }
        if(isInCheck(teamTurn)){
            gameBoard = copy;
            throw Exception;//./..............not right
        }
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
        board.stageBoard();
    }

    @Override
    public Board getBoard() {
        return gameBoard;
    }
}
