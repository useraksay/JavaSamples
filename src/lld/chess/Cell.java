package lld.chess;

public class Cell {
    int row;
    int col;
    Color color;
    Piece piece;

    public Cell(int row, int col, Color color, Piece piece) {
        this.row = row;
        this.col = col;
        this.color = color;
        this.piece = piece;
    }

    public Cell() {}

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    @Override
    public String toString() {
        return "row: " + row + ", col: " + col + ", color: " + color + ", piece: " + piece;
    }
}
