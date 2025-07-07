package lld.chess;

public class Game {
    public static void main(String[] args) {
        Game game = new Game();
        Player player1 = new Player("", Color.BLACK);
        Player player2 = new Player("", Color.WHITE);
        Board board = Board.getBoard();
        intialise(board);
    }

    private static void intialise(Board board) {
        Cell[][] cells = board.getCells();
        initialiseColor(cells);
        //intialise pieces
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                System.out.print(cells[row][col]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    /*
    * initialise color of the cell
    * odd columns as black and even columns as white
    * */
    private static void initialiseColor(Cell[][] cells) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (row % 2 == 0) {
                    if (col % 2 == 0) {
                        if (cells[row][col] == null) {
                            cells[row][col] = new Cell();
                            cells[row][col].setRow(row + 1);
                            cells[row][col].setCol(col + 1);
                            cells[row][col].setColor(Color.WHITE);
                        } else {
                            cells[row][col].setRow(row + 1);
                            cells[row][col].setCol(col + 1);
                            cells[row][col].setColor(Color.WHITE);
                        }
                    } else {
                        if (cells[row][col] == null) {
                            cells[row][col] = new Cell();
                            cells[row][col].setRow(row + 1);
                            cells[row][col].setCol(col + 1);
                            cells[row][col].setColor(Color.BLACK);
                        } else {
                            cells[row][col].setRow(row + 1);
                            cells[row][col].setCol(col + 1);
                            cells[row][col].setColor(Color.BLACK);
                        }
                    }
                } else {
                    if (col % 2 == 0) {
                        if (cells[row][col] == null) {
                            cells[row][col] = new Cell();
                            cells[row][col].setRow(row + 1);
                            cells[row][col].setCol(col + 1);
                            cells[row][col].setColor(Color.BLACK);
                        } else {
                            cells[row][col].setRow(row + 1);
                            cells[row][col].setCol(col + 1);
                            cells[row][col].setColor(Color.BLACK);
                        }
                    } else {
                        if (cells[row][col] == null) {
                            cells[row][col] = new Cell();
                            cells[row][col].setRow(row + 1);
                            cells[row][col].setCol(col + 1);
                            cells[row][col].setColor(Color.WHITE);
                        } else {
                            cells[row][col].setRow(row + 1);
                            cells[row][col].setCol(col + 1);
                            cells[row][col].setColor(Color.WHITE);
                        }
                    }
                }
            }
        }
    }
}
