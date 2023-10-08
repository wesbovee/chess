package chess;

import java.util.Objects;

public class Position implements ChessPosition{
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
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Position position = (Position) object;
        return row == position.row && column == position.column;
    }

    @Override
    public int hashCode() {
       int hashVal = ((row -1) * 8) + column;
       return hashVal;
    }
}
