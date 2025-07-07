package lld.chess;

public abstract class Piece {
    Color color;
    boolean isAlive;

    public Piece(Color color, boolean isAlive) {
        this.color = color;
        this.isAlive = isAlive;
    }

    public abstract boolean validMove();
}
