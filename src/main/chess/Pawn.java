package chess;

import java.util.Collection;

public class Pawn extends Piece {
    public Pawn(ChessGame.TeamColor color){
        this.color = color;
        type = PieceType.PAWN;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
    }

}
