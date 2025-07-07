package lld.chess;

public class Player {
    String name;
    Color color;

    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public void makeMove(Cell from, Cell to) {
        int fromRow = from.getRow();
        int fromCol = from.getCol();
        Piece piece = from.getPiece();
    }
}
