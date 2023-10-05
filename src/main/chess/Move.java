package chess;

public class Move implements ChessMove{
    private Position start;
    private Position end;
    private ChessPiece.PieceType promotion;
    public Move(Position st, Position en, Piece.PieceType promo){
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
