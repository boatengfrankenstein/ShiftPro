/**
 * Created by dan on 6/20/16.
 */
public abstract class AbstractPro {
    public abstract class AbstractMove {

        protected int[][] grid;
        private int gridDimension;
        private int points = 0;

        public AbstractMove(int[][] grid, int gridDimension) {
            this.gridDimension = gridDimension;
            this.grid = grid;
        }

        public abstract AbstractMove start();

        /**
         * this method will move the tiles into left
         * <p>
         * take note that:
         * <ul>
         * <li>if there is a 0 tile skip it</li>
         * <li>if the grid cell can move, move them into left</li>
         * <li>after moving if two grid cell stay after each other and have different value, just stop moving</li>
         * <li>if two tile have the same value, merge them and add point</li>
         * </ul>
         * </p>
         *
         * @return return the grid after normalization
         */
        public final int[][] pusher() {
            //from right to left check none value zero for pushing to next cell
            for (int row = 0; row < gridDimension; ++row) {
                for (int col = 1; col < gridDimension; ++col) {
                    int currentCellValue = grid[row][col];
                    if (currentCellValue == 0) {
                        continue;
                    }

                    //start to the left if its not the first cell value and one cell left has 0 value
                    int preCellPosition = col - 1;
                    int lastMergePosition = 0;

                    for (; preCellPosition > lastMergePosition && grid[row][preCellPosition] == 0; ) {
                        --preCellPosition;
                    }

                    int preCellValue = grid[row][preCellPosition];

                    if (preCellPosition == col) {
                    } else if (preCellValue == 0) {
                        grid[row][preCellPosition] = currentCellValue;
                        grid[row][col] = 0;
                    } else if (preCellValue == currentCellValue) {
                        mergeColumns(row, col, preCellPosition);
                    } else if (preCellValue != currentCellValue && preCellPosition + 1 != col) {
                        grid[row][preCellPosition + 1] = grid[row][col];
                        grid[row][col] = 0;
                    }
                }
            }
            return grid;
        }


        /**
         * if two cell are the same and there is a movement toward them
         * they merge and the acquired point is added to the system
         *
         * @param row
         * @param col
         * @param previousPosition
         */
        private void mergeColumns(int row, int col, int previousPosition) {
            //The user's score is incremented whenever two tiles combine, by the
            //value of the new tile.
            grid[row][previousPosition] *= 2;
            grid[row][col] = 0;
            points += grid[row][previousPosition];
        }

        /**
         * provide new acquired point
         *
         * @return
         */
        public int getPoints() {
            return points;
        }

        /**
         * can get call
         * to return the new acquired grid
         *
         * @return
         */
        public int[][] newGrid() {
            return grid;
        }


        /**
         * This method will iterate over the grid and
         * rotate the whole grid into the right by 90 degrees
         *
         * @return
         */
        public int[][] rotateRight() {
            int[][] rotatedBoard = new int[gridDimension][gridDimension];

            for (int i = 0; i < gridDimension; ++i) {
                for (int j = 0; j < gridDimension; ++j) {
                    rotatedBoard[i][j] = grid[gridDimension - j - 1][i];
                }
            }

            grid = rotatedBoard;
            return grid;
        }

        /**
         * This method will iterate over the grid and
         * rotate the whole grid into the left by 90 degrees
         *
         * @return
         */
        public int[][] rotateLeft() {
            int[][] rotatedBoard = new int[gridDimension][gridDimension];

            for (int i = 0; i < gridDimension; ++i) {
                for (int j = 0; j < gridDimension; ++j) {
                    rotatedBoard[gridDimension - j - 1][i] = grid[i][j];
                }
            }

            grid = rotatedBoard;
            return grid;
        }
    }
}