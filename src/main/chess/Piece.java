package chess;

import java.util.Collection;

public abstract class Piece implements ChessPiece{
    protected ChessGame.TeamColor color;
    protected ChessPiece.PieceType type;
    @Override
    public ChessGame.TeamColor getTeamColor() {
        return color;
    }

    @Override
    public ChessPiece.PieceType getPieceType() {
        return type;
    }

    @Override
    public abstract Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition);
}
