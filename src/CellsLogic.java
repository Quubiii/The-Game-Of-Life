public class CellsLogic {
    private final int rows;
    private final int columns;

    public CellsLogic(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    public int countAliveNeighbors(boolean[][] status, int cellRow, int cellColumn) {
        int counter = 0;
        for(int i = -1; i <= 1; i++) {
            for(int j = -1; j <= 1; j++) {
                if(i == 0 && j == 0) {
                    continue;
                }
                int possibleNeighborRow = cellRow + i;
                int possibleNeighborColumn = cellColumn + j;
                if(possibleNeighborRow >= 0 && possibleNeighborColumn >= 0 &&
                        possibleNeighborRow < status.length && possibleNeighborColumn < status[0].length &&
                        status[possibleNeighborRow][possibleNeighborColumn]) {
                    counter += 1;
                }
            }
        }
        return counter;
    }

    public boolean[][] nextGeneration(boolean[][] board) {
        boolean[][] nextGenBoard = new boolean[rows][columns];

        for(int row = 0; row < rows; row++) {
            for(int column = 0; column < columns; column++) {
                int aliveNeighbors = countAliveNeighbors(board, row, column);

                if(board[row][column]) {
                    nextGenBoard[row][column] = aliveNeighbors == 2 || aliveNeighbors == 3;
                } else {
                    nextGenBoard[row][column] = aliveNeighbors == 3;
                }
            }
        }
        return nextGenBoard;
    }
}
