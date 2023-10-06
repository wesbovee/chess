package chess;

public class Board implements ChessBoard{
    public ChessPiece[][] layout;
    public Board(){
        layout = new ChessPiece[8][8];
    }
    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {
        layout[position.getColumn() - 1][position.getRow() - 1] = piece;
    }

    @Override
    public ChessPiece getPiece(ChessPosition position) {
        return layout[position.getColumn() - 1][position.getRow() - 1 ];
    }

    @Override
    public void resetBoard() {
        clearBoard();
        stageBoard();
    }

    private void clearBoard(){
        for(int c = 0; c<8; c++){
            for(int r=0; r<8; r++){
                layout[c][r] = null;
            }
        }
    }

    public void stageBoard(){
        int c = 0;
        int w = 0;
        int b = 7;
        layout[c][w] = new Rook(ChessGame.TeamColor.WHITE);
        layout[c][b] = new Rook(ChessGame.TeamColor.BLACK);
        c++;
        layout[c][w] = new Knight(ChessGame.TeamColor.WHITE);
        layout[c][b] = new Knight(ChessGame.TeamColor.BLACK);
        c++;
        layout[c][w] = new Bishop(ChessGame.TeamColor.WHITE);
        layout[c][b] = new Bishop(ChessGame.TeamColor.BLACK);
        c++;
        layout[c][w] = new Queen(ChessGame.TeamColor.WHITE);
        layout[c][b] = new Queen(ChessGame.TeamColor.BLACK);
        c++;
        layout[c][w] = new King(ChessGame.TeamColor.WHITE);
        layout[c][b] = new King(ChessGame.TeamColor.BLACK);
        c++;
        layout[c][w] = new Bishop(ChessGame.TeamColor.WHITE);
        layout[c][b] = new Bishop(ChessGame.TeamColor.BLACK);
        c++;
        layout[c][w] = new Knight(ChessGame.TeamColor.WHITE);
        layout[c][b] = new Knight(ChessGame.TeamColor.BLACK);
        c++;
        layout[c][w] = new Rook(ChessGame.TeamColor.WHITE);
        layout[c][b] = new Rook(ChessGame.TeamColor.BLACK);
        w++;
        b--;
        while(c > -1){
            layout[c][w] = new Pawn(ChessGame.TeamColor.WHITE);
            layout[c][b] = new Pawn(ChessGame.TeamColor.BLACK);
            c--;
        }
    }
}
