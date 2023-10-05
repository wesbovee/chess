package chess;

import java.util.Collection;

public class Knight extends Piece {
    public Knight(ChessGame.TeamColor color){
        this.color = color;
        type = PieceType.KNIGHT;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
    }
}
