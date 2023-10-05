package chess;

import java.util.Collection;

public class Queen extends Piece {
    public Queen(ChessGame.TeamColor color){
        this.color = color;
        type = PieceType.QUEEN;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
    }
}
