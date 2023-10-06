package chess;

import java.util.Collection;
import java.util.HashSet;
import static chess.ChessGame.TeamColor.WHITE;

public class Pawn extends Piece {
    public Pawn(ChessGame.TeamColor color){
        this.color = color;
        type = PieceType.PAWN;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new HashSet<ChessMove>();
        int r = myPosition.getRow();
        PieceType promo = null;
        if (this.color == WHITE) {
            if(r == 2) {
                shift(board, myPosition, moves, 0, 2,promo);
            }
            if(r == 7) {
                promo = PieceType.QUEEN;
            }
            for(int i = -1; i<2; i++){
                shift(board, myPosition, moves, i, 1,promo);
            }
        } else {
            if(r == 7) {
                shift(board, myPosition, moves, 0, -2,promo);
            }
            if(r == 2) {
                promo = PieceType.QUEEN;
            }
            for(int i = -1; i<2; i++){
                shift(board, myPosition, moves, i, -1,promo);
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
