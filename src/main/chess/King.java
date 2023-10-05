package chess;

import java.util.Collection;
import java.util.HashSet;

public class King extends Piece {
    public King(ChessGame.TeamColor color){
        this.color = color;
        type = PieceType.KING;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new HashSet<ChessMove>();

        return moves;
    }

}
