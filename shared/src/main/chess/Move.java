package chess;

import java.util.Objects;

public class Move implements ChessMove{
    /**
     *
     */
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
        if(object != null && this.getClass() == object.getClass()) {
            Move moveTwo = (Move) object;
            if (start.equals(moveTwo.start) && end.equals(moveTwo.end) && Objects.equals(promotion, moveTwo.getPromotionPiece())) {
                return true;
            }
        }
        return false;
    }

    @Override
    //redo
    public int hashCode() {
        return(start.hashCode() * 64) + end.hashCode();
    }
}
