package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import static chess.ChessGame.TeamColor.WHITE;

public class Pawn extends Piece {
    /**
     *
     * @param color
     */
    public Pawn(ChessGame.TeamColor color){
        this.color = color;
        type = PieceType.PAWN;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new HashSet<ChessMove>();
        int r = myPosition.getRow();
        int c = myPosition.getColumn();
        Collection <PieceType> promos = new ArrayList<>();
        if (this.color == WHITE) {
            if(r == 2 && (board.getPiece(new Position(c,4))) == null && (board.getPiece(new Position(c,3))) == null) {
                shift(board, myPosition, moves, 0, 2,null);
            }
            if(r == 7) {
                promos.add(PieceType.ROOK);
                promos.add(PieceType.KNIGHT);
                promos.add(PieceType.QUEEN);
                promos.add(PieceType.BISHOP);
            } else { promos.add(null); }

            if(board.getPiece(new Position(c, r+1)) == null){
                for(PieceType p : promos) {
                    shift(board, myPosition, moves, 0, 1,p);
                }
            }
            if(c>1) {
                if (board.getPiece(new Position(c - 1, r + 1)) != null) {
                    if (board.getPiece(new Position(c - 1, r + 1)).getTeamColor() != this.color) {
                        for (PieceType p : promos) {
                            shift(board, myPosition, moves, -1, 1, p);
                        }
                    }
                }
            }
            if(c<8) {
                if (board.getPiece(new Position(c + 1, r + 1)) != null) {
                    if (board.getPiece(new Position(c + 1, r + 1)).getTeamColor() != this.color) {
                        for (PieceType p : promos) {
                            shift(board, myPosition, moves, 1, 1, p);
                        }
                    }
                }
            }
        } else {
            if(r == 7 && (board.getPiece(new Position(c,5))) == null && (board.getPiece(new Position(c,6))) == null) {
                shift(board, myPosition, moves, 0, -2,null);
            }
            if(r == 2) {
                promos.add(PieceType.ROOK);
                promos.add(PieceType.KNIGHT);
                promos.add(PieceType.QUEEN);
                promos.add(PieceType.BISHOP);
            } else { promos.add(null); }
            if(board.getPiece(new Position(c, r-1)) == null){
                for(PieceType p : promos) {
                    shift(board, myPosition, moves, 0, -1, p);
                }
            }
            if(c>1) {
                if (board.getPiece(new Position(c - 1, r - 1)) != null) {
                    if (board.getPiece(new Position(c - 1, r - 1)).getTeamColor() != this.color) {
                        for (PieceType p : promos) {
                            shift(board, myPosition, moves, -1, -1, p);
                        }
                    }
                }
            }
            if (c<8) {
                if (board.getPiece(new Position(c + 1, r - 1)) != null) {
                    if (board.getPiece(new Position(c + 1, r - 1)).getTeamColor() != this.color) {
                        for (PieceType p : promos) {
                            shift(board, myPosition, moves, 1, -1, p);
                        }
                    }
                }
            }
        }
        return moves;
    }

    private void shift(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> moves, int c_direction,int r_direction,PieceType promo) {
        int c = myPosition.getColumn();
        int r = myPosition.getRow();
        c += c_direction;
        r += r_direction;
        if (c>0 && c< 9 && r>0 && r<9) {
            Position pos = new Position(c,r);
            if (board.getPiece(pos) == null) {
                moves.add(new Move(myPosition,pos,promo));
            } else if (board.getPiece(pos).getTeamColor() != this.color) {
                moves.add(new Move(myPosition,pos,promo));
            }
        }
    }
}
