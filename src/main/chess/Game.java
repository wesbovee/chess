package chess;

import java.util.Collection;
import java.util.HashSet;

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
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        if(gameBoard.getPiece(startPosition) == null){
            return null;
        }
        Collection<ChessMove> potential =gameBoard.getPiece(startPosition).pieceMoves(gameBoard, startPosition);
        Collection<ChessMove> pass = new HashSet<>();
        for(ChessMove p : potential) {
            if (testMove(p)) {
                pass.add(p);
            }
        }
        if(pass.isEmpty()) {
            return null;
        }
        return pass;
    }

    @Override
    public void makeMove(ChessMove move) throws InvalidMoveException {
        Collection<ChessMove> valid = validMoves(move.getStartPosition());
        if(valid.contains(move)){
            gameBoard.addPiece(move.getEndPosition(), gameBoard.getPiece(move.getStartPosition()));
            gameBoard.addPiece(move.getStartPosition(), null);
        } else {
            throw new InvalidMoveException("This move is illegal");
        }
    }

    @Override
    public boolean isInCheck(TeamColor teamColor) {
        Collection<ChessMove> possible_attacks = new HashSet<>();
        Position king = null;
        for(int c = 0; c<8; c++){
            for(int r=0; r<8; r++){
                ChessPiece temp = gameBoard.getPiece(new Position(c+1,r+1));
                if(temp != null){
                    if (temp.getTeamColor() != teamColor) {
                        possible_attacks.addAll(temp.pieceMoves(gameBoard,new Position(c+1,r+1)));
                    }
                    if (temp.getTeamColor() == teamColor && temp.getPieceType() == ChessPiece.PieceType.KING) {
                        king = new Position(c+1, r+1);
                    }
                }
            }
        }
        boolean check = false;
        for(ChessMove m : possible_attacks) {
            if(m.getEndPosition() == king) {
                check = true;
            }
        }
        return check;
    }

    @Override
    public boolean isInCheckmate(TeamColor teamColor) {
        Position king = null;
        for(int c = 0; c<8; c++) {
            for (int r = 0; r < 8; r++) {
                ChessPiece temp = gameBoard.getPiece(new Position(c + 1, r + 1));
                if (temp.getTeamColor() == teamColor && temp.getPieceType() == ChessPiece.PieceType.KING) {
                    king = new Position(c + 1, r + 1);
                }
            }
        }
        if (king != null) {
            Collection<ChessMove> king_moves = gameBoard.getPiece(king).pieceMoves(gameBoard,king);
            king_moves.add(new Move(king,king,null));
            for(ChessMove k : king_moves) {
                if(testMove(k)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean isInStalemate(TeamColor teamColor) {
        Collection<ChessMove> possible_attacks = new HashSet<>();
        for(int c = 0; c<8; c++) {
            for (int r = 0; r < 8; r++) {
                ChessPiece temp = gameBoard.getPiece(new Position(c+1,r+1));
                if(temp != null){
                     if (temp.getTeamColor() != teamColor) {
                        possible_attacks.addAll(validMoves(new Position(c,r)));
                    }
                }
            }
        }
        if(possible_attacks.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public void setBoard(ChessBoard board) {
        this.gameBoard = (Board) board;
    }

    @Override
    public Board getBoard() {
        return gameBoard;
    }
    public boolean testMove(ChessMove move) {
        Board copy = gameBoard;
        gameBoard.addPiece(move.getEndPosition(), gameBoard.getPiece(move.getStartPosition()));
        gameBoard.addPiece(move.getStartPosition(), null);

        if(isInCheck(this.teamTurn)){
            gameBoard = copy;
            return false;
        }
        gameBoard = copy;
        return true;
    }
}
