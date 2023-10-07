package chess;

import java.util.Objects;

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

    @Override
    //redo
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Move move = (Move) object;
        //redo
        return Objects.equals(start, move.start) && Objects.equals(end, move.end) && promotion == move.promotion;
    }

    @Override
    //redo
    public int hashCode() {
        return Objects.hash(start, end, promotion);
    }
}
