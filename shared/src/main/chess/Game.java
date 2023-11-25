package chess;

import java.util.Collection;
import java.util.HashSet;

import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessGame.TeamColor.WHITE;

public class Game implements ChessGame{
    /**
     *
     */

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
        Collection<ChessMove> potential = gameBoard.getPiece(startPosition).pieceMoves(gameBoard, startPosition);
        Collection<ChessMove> pass = new HashSet<>();
        for(ChessMove p : potential) {
            if (isGoodMove(p)) {
                pass.add(p);
            }
        }
        return pass;
    }

    @Override
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition start = move.getStartPosition();
        ChessPosition end = move.getEndPosition();
        if (gameBoard.getPiece(start) != null) {
            if (gameBoard.getPiece(start).getTeamColor() != getTeamTurn()) {
                throw new InvalidMoveException("It is not your turn");
            }
            if (0 < end.getColumn() && 0 < start.getColumn() && end.getColumn() < 9 && start.getColumn() < 9 && 0 < end.getRow() && 0 < start.getRow() && end.getRow() < 9 && start.getRow() < 9) {
                Collection<ChessMove> valid = validMoves(move.getStartPosition());
                if (valid != null) {
                    if (valid.contains(move)) {
                        if (move.getPromotionPiece() == ChessPiece.PieceType.QUEEN) {
                            gameBoard.addPiece(move.getEndPosition(), new Queen(gameBoard.getPiece(move.getStartPosition()).getTeamColor()));
                        } else if (move.getPromotionPiece() == ChessPiece.PieceType.ROOK) {
                            gameBoard.addPiece(move.getEndPosition(), new Rook(gameBoard.getPiece(move.getStartPosition()).getTeamColor()));
                        } else if (move.getPromotionPiece() == ChessPiece.PieceType.KNIGHT) {
                            gameBoard.addPiece(move.getEndPosition(), new Knight(gameBoard.getPiece(move.getStartPosition()).getTeamColor()));
                        } else if (move.getPromotionPiece() == ChessPiece.PieceType.BISHOP) {
                            gameBoard.addPiece(move.getEndPosition(), new Bishop(gameBoard.getPiece(move.getStartPosition()).getTeamColor()));
                        } else {
                            gameBoard.addPiece(move.getEndPosition(), gameBoard.getPiece(move.getStartPosition()));
                        }
                        gameBoard.addPiece(move.getStartPosition(), null);
                        if (getTeamTurn() == WHITE) {
                            setTeamTurn(BLACK);
                        } else {
                            setTeamTurn(WHITE);
                        }
                    } else {
                        throw new InvalidMoveException("This move is illegal");
                    }
                } else {
                    throw new InvalidMoveException("This move is illegal");
                }
            } else {
                throw new InvalidMoveException("This move either starts or ends off the board");
            }
        } else {
        throw new InvalidMoveException("There is no piece at the start position");
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
                        Collection<ChessMove> temp_valid_moves = temp.pieceMoves(gameBoard, new Position(c+1,r+1));
                        possible_attacks.addAll(temp_valid_moves);
                    }
                    if (temp.getTeamColor() == teamColor && temp.getPieceType() == ChessPiece.PieceType.KING) {
                        king = new Position(c+1, r+1);
                    }
                }
            }
        }
        boolean check = false;
        for(ChessMove m : possible_attacks) {
            if(m.getEndPosition().equals(king)) {
                check = true;
            }
        }
        return check;
    }

    @Override
    public boolean isInCheckmate(TeamColor teamColor) {
        Position king = null;
        Collection<ChessMove> possibleMoves = new HashSet<>();
        for(int c = 1; c<9; c++) {
            for (int r = 1; r < 9; r++) {
                ChessPiece temp = gameBoard.getPiece(new Position(c , r));
                if (temp != null) {
                    if (temp.getTeamColor() == teamColor) {
                        possibleMoves.addAll(temp.pieceMoves(gameBoard, new Position(c,r)));
                        if(temp.getPieceType() == ChessPiece.PieceType.KING) {
                            king = new Position(c , r );
                        }
                    }
                }
            }
        }
        if (king != null) {
            for(ChessMove m : possibleMoves) {
                if(isGoodMove(m)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean isInStalemate(TeamColor teamColor) {
        Collection<ChessMove> possible_attacks = new HashSet<>();
        for(int c = 1; c<9; c++) {
            for (int r = 1; r < 9; r++) {
                ChessPiece temp = gameBoard.getPiece(new Position(c,r));
                if(temp != null){
                     if (temp.getTeamColor() == teamColor) {
                         if ( validMoves(new Position(c,r)) != null) {
                             possible_attacks.addAll(validMoves(new Position(c, r)));
                         }
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
    public boolean isGoodMove(ChessMove move) {
        Board copy = copyBoard();
        if (gameBoard.getPiece(move.getStartPosition()) != null) {
            gameBoard.addPiece(move.getEndPosition(), gameBoard.getPiece(move.getStartPosition()));
            gameBoard.addPiece(move.getStartPosition(), null);

            if (isInCheck(gameBoard.getPiece(move.getEndPosition()).getTeamColor())) {
                gameBoard = copy;
                return false;
            }
            gameBoard = copy;
            return true;
        }
        return false;
    }
    public Board copyBoard () {
        Board copy = new Board();
        for( int i=0; i<8; i++){
            for (int j = 0; j<8; j++){
                copy.layout[i][j] = gameBoard.layout[i][j];
            }
        }
        return copy;
    }
}
