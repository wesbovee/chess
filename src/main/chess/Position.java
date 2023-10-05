package chess;

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
}
