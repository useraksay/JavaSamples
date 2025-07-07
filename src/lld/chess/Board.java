package lld.chess;

public class Board {
    Cell[][] cells = new Cell[8][8];
    private static Board board;

    public Cell[][] getCells() {
        return cells;
    }

    private Board() {}

    public static Board getBoard() {
        if (board == null) {
            board = new Board();
            return board;
        }

        return board;
    }
}
