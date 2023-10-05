package chess;

import java.util.Collection;
import java.util.HashSet;

public class Pawn extends Piece {
    public Pawn(ChessGame.TeamColor color){
        this.color = color;
        type = PieceType.PAWN;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new HashSet<ChessMove>();

        return moves;
    }

}
