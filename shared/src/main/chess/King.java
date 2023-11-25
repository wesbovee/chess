package chess;

import java.util.Collection;
import java.util.HashSet;

public class King extends Piece {
    /**
     *
     * @param color
     */
    public King(ChessGame.TeamColor color){
        this.color = color;
        type = PieceType.KING;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new HashSet<ChessMove>();
        for(int i = -1; i<2; i++) {
            for(int j = -1; j<2; j++) {
                shift(board, myPosition, moves, i,j);
            }
        }
        return moves;
    }

    private void shift(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> moves, int c_direction,int r_direction) {
        int c = myPosition.getColumn();
        int r = myPosition.getRow();
        c += c_direction;
        r += r_direction;
        if (c>0 && c< 9 && r>0 && r<9) {
            Position pos = new Position(c,r);
            if (board.getPiece(pos) == null) {
                moves.add(new Move(myPosition,pos,null));
            } else if (board.getPiece(pos).getTeamColor() != this.color) {
                moves.add(new Move(myPosition,pos,null));
            }
        }
    }
}