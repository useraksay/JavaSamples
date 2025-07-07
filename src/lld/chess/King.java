package lld.chess;

public class King extends Piece {
    public King(Color color, boolean isAlive) {
        super(color, isAlive);
    }

    @Override
    public boolean validMove() {
        return false;
    }
}
