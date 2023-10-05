package chess;

import java.util.Collection;

public class Bishop extends Piece {
    public Bishop(ChessGame.TeamColor color){
        this.color = color;
        type = PieceType.BISHOP;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
    }
}
