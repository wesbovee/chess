package chess;

import java.util.Collection;

public class Rook extends Piece {
    public Rook(ChessGame.TeamColor color){
        this.color = color;
        type = PieceType.ROOK;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
    }
}
