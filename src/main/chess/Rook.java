package chess;

import java.util.Collection;
import java.util.HashSet;

public class Rook extends Piece {
    public Rook(ChessGame.TeamColor color){
        this.color = color;
        type = PieceType.ROOK;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new HashSet<ChessMove>();
        shift(board, myPosition, moves, 1,0);
        shift(board, myPosition, moves, 0,-1);
        shift(board, myPosition, moves, -1,0);
        shift(board, myPosition, moves, 0,1);
        return moves;
    }

    private void shift(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> moves, int c_direction,int r_direction) {
        int c = myPosition.getColumn();
        int r = myPosition.getRow();
        while(true) {
            c += c_direction;
            r += r_direction;
            if (c > 0 && c < 9 && r > 0 && r < 9) {
                Position pos = new Position(c, r);
                if (board.getPiece(pos) == null) {
                    moves.add(new Move(myPosition, pos, null));
                } else if (board.getPiece(pos).getTeamColor() != this.color) {
                    moves.add(new Move(myPosition, pos, null));
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }
}
