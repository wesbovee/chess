package chess;

public class Move implements ChessMove{
    private ChessPosition start;
    private ChessPosition end;
    private ChessPiece.PieceType promotion;
    public Move(ChessPosition st, ChessPosition en, ChessPiece.PieceType promo){
        start = st;
        end = en;
        promotion = promo;
    }
    @Override
    public ChessPosition getStartPosition() {
        return start;
    }

    @Override
    public ChessPosition getEndPosition() {
        return end;
    }

    @Override
    public ChessPiece.PieceType getPromotionPiece() {
        return promotion;
    }
}
