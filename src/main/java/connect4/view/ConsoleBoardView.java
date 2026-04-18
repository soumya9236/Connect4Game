package connect4.view;

import connect4.board.Board;

public class ConsoleBoardView implements BoardView {

    @Override
    public void display(Board board) {
        System.out.println();

        for (int r = 0; r < board.getRows(); r++) {
            System.out.print("| ");
            for (int c = 0; c < board.getCols(); c++) {
                System.out.print(board.getCell(r, c) + " ");
            }
            System.out.println("|");
        }

        System.out.print("  ");
        for (int c = 0; c < board.getCols(); c++) {
            System.out.print(c + " ");
        }
        System.out.println("\n");
    }
}