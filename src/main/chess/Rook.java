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

        return moves;
    }
}
