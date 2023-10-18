package chess;

import java.util.Objects;

public class Position implements ChessPosition{
    /**
     *
     */
    private int row;
    private int column;
    public Position(int c, int r){
        this.row = r;
        this.column = c;
    }
    @Override
    public int getRow() {
        //1-8
        return this.row;
    }

    @Override
    public int getColumn() {
        //1-8, a-h eventually
        return this.column;
    }

    @Override
    public boolean equals(Object object) {
        if(object != null && this.getClass() == object.getClass()) {
            Position posTwo = (Position) object;
            return this.row == posTwo.getRow() && this.column == posTwo.getColumn();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ((row -1) * 8) + column;
    }
}
