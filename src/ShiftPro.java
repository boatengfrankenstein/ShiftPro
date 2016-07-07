public  class ShiftPro {


    protected int[][] grid;
    private int gridDimension;
    private int points = 0;
    int flag = 0;
    private boolean hasshift = false;

    public ShiftPro(int[][] grid, int gridDimension) {
        this.gridDimension = gridDimension;
        this.grid = grid;
    }


    public static void main(String args[]) {

        System.out.println("HEllo");

        int before[][] = {
                {2, 2, 0, 4},
                {5, 5, 0, 8,},
                {9, 5, 9, 9,},
                {0, 0, 0, 16,},

        };

        ShiftPro me = new ShiftPro(before, 4);
        // me.pusher();
        //  printTable(me.pusher());
        System.out.println("Flag" + me.can_move());

        me.mymovtedown();
      //  printTable(me.Left(before));
        printTable(me.grid);


    }

    public static void printTable(int[][] table) {

        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table.length; j++) {
                System.out.print(String.format("%4s", table[i][j]));
            }
            System.out.println();
        }
    }

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
    public void movetoright() {
        rotateLeft();
        rotateLeft();
        pusher();
        rotateRight();
        rotateRight();
    }

    public void movedown() {
        rotateRight();
        pusher();
        rotateLeft();

    }

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

    public int getflag() {
        return this.flag;
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


    ////////////my left
    public int[][] Left(int[][] grid) {
        for (int y = 1; y <= grid[0].length - 1; y++) {
            for (int x = 0; x >= grid.length; x++) {

                while (true) {

                    //When two number match
                    if (grid[x][y] == grid[x][y - 1]) {
                        grid[x][y - 1] = grid[x][y] + grid[x][y - 1];
                        grid[x][y] = 0;
                        break;
                    }
                    //isLineFull full=new isLineFull();

                    if (grid[x][y - 1] == 0) {
                        grid[x][y - 1] = grid[x][y];
                        grid[x][y] = 0;
                    }

                    break;
                }


            }
        }

        return grid;
    }

/////////////////////////////////
    //C implementnation
    ////////////////////////////


    public boolean is_outside(int x, int y) {
        return (x < 0 || x >= grid.length || y < 0 || y >= this.grid[0].length);
    }

    ////////////// my down shift //////////
    public void myDownShift() {
        for (int x = 0; x < grid.length; ++x) {
            for (int y = grid[0].length-1 ; y >= 0; --y) {
                // Empty slots dont move
                if (grid[y][x] == 0) {
                    //
                    continue;
                }

                int newY = y;
                int nextY = y + 1;

                while (grid[nextY][x] == 0 && !is_outside(x, nextY)) {
                    newY = nextY;

                    ++nextY;
                }

                if (newY != y) {
                    hasshift = true;
                }

                int value = grid[y][x];

                grid[y][x] = 0;
                grid[newY][x] = value;
            }
        }

    }



   public void mymovedown() {
       //first for loop is for shifting all the elements down
       //for each element on the gameboard apart from the top row
       for (int i = 1; i < grid.length; i++) {
           for (int j = 0; j < grid[0].length; j++) {
               // if the element isn't 0 then
               if (grid[i][j] != 0) {
                   // for every element above the current element starting from the top
                   for (int k = 0; k < i; k++) {
                       //if the element is 0 then
                       if (grid[k][j] == 0) {
                           //move the current element to the position of the 0 element above it
                           grid[k][j] = grid[i][j];
                           grid[i][j] = 0;
                       }
                   }
               }
           }
       }

       //second for loop is for combining numbers
       //for each element in the array apart from the top row
       for (int i = 1; i < grid.length; i++) {
           for (int j = 0; j < grid[0].length; j++) {
               // if the current element is the same as the one above it
               if (grid[i][j] == grid[i - 1][j]) {
                   //combine the two
                   grid[i - 1][j] = grid[i][j] * 2;
                   //for all other elements below it
                   for (int k = i; k < grid.length; k++) {
                       //if it is the bottom row
                       if (k == grid.length - 1) {
                           //add a zero to the bottom row
                           grid[k][j] = 0;
                       } else {
                           //otherwise shift the other elements to the position above
                           grid[k][j] = grid[k + 1][j];
                           grid[k + 1][j] = 0;
                       }
                   }
               }
           }
       }

   }


    public void moveUPward(){


        //first for loop is for shifting all the elements down
        //for each element on the gameboard apart from the left column
        for(int i = 0; i < grid.length; i++){
            for(int j = 1; j < grid[0].length; j++){
                // if the element is 0 then move to the next one
                if(grid[i][j] != 0){
                    // for every element to the right of the current element starting from the left
                    for (int k = 0; k < j; k++){
                        //if the element is 0 then
                        if (grid[i][k] == 0){
                            //move the current element to the position of the 0 element to the left of it
                            grid[i][k] = grid[i][j];
                            grid[i][j] = 0;
                        }
                    }
                }
            }
        }

        //second for loop is for combining numbers
        //for each element in the array apart from the top row
        for(int i = 0; i < grid.length; i++){
            for (int j = 1; j < grid[0].length; j++){
                //if the current element is equal to the element on the left
                if (grid[i][j] == grid[i][j-1]){
                    //combine the elements
                    grid[i][j-1] = grid[i][j] * 2;
                    //for each element to the right of current element
                    for (int k = j; k < grid[0].length; k++){
                        //if the far right element
                        if (k == grid[0].length - 1){
                            //set equal to 0
                            grid[i][k] = 0;
                        }else{
                            //if not the far right element set equal to the element on the right
                            grid[i][k] = grid[i][k+1];
                            //and set the element on the right equal to 0
                            grid[i][k+1] = 0;
                        }
                    }
                }
            }
        }

    }

    public void mymovtedown(){
        //first for loop is for shifting all the elements down
        //for each element on the gameboard apart from the bottom row
        for(int i = this.grid.length-2; i >=0; i--){
            for(int j = 0; j < this.grid[0].length; j++){
                // if the element isn't 0 then
                if(grid[i][j] != 0){
                    // for every element below the current element starting from the bottom
                    for (int k = this.grid.length-1; k >= i; k--){
                        //if the element is 0 then
                        if (grid[k][j] == 0){
                            //move the current element to the position of the 0 element below it
                            grid[k][j] = grid[i][j];
                            grid[i][j] = 0;
                        }
                    }
                }
            }
        }

        //second for loop is for combining numbers
        //for each element in the array apart from the bottom row
        for(int i = this.grid.length-2; i >= 0; i--){
            for (int j = 0; j < this.grid[0].length; j++){
                // if the current element is the same as the one below it
                if (grid[i][j] == grid[i+1][j]){
                    //combine the two
                    grid[i+1][j] = grid[i][j] * 2;
                    //for all other elements above it
                    for (int k = i; k >= 0; k--){
                        //if it is the top row
                        if (k == 0){
                            //add a zero to the top row
                            grid[k][j] = 0;
                        }else{
                            //otherwise shift the other elements to the position below
                            grid[k][j] = grid[k-1][j];
                            grid[k-1][j] = 0;
                        }
                    }
                }
            }
        }
    }

    public void movetorigth(){


        //first for loop is for shifting all the elements down
        //for each element on the gameboard apart from the right column
        for(int i = 0; i < this.grid.length; i++){
            for(int j = this.grid[0].length - 2; j >= 0; j--){
                // if the element is 0 then move to the next one
                if(this.grid[i][j] != 0){
                    // for every element to the right of the current element starting from the right
                    for (int k = this.grid[0].length-1; k > j; k--){
                        //if the element is 0 then
                        if (this.grid[i][k] == 0){
                            //move the current element to the position of the 0 element to the right of it
                            this.grid[i][k] = this.grid[i][j];
                            this.grid[i][j] = 0;
                        }
                    }
                }
            }
        }

        //second for loop is for combining numbers
        //for each element in the array apart from the right column
        for(int i = 0; i < this.grid.length; i++){
            for (int j = this.grid[0].length-2; j >= 0; j--){
                //if the current element is equal to the element on the right
                if (this.grid[i][j] == this.grid[i][j+1]){
                    //combine the elements
                    this.grid[i][j+1] = this.grid[i][j] * 2;
                    //for each element to the left of current element
                    for (int k = j; k >= 0; k--){
                        //if the far left element
                        if (k == 0){
                            //set equal to 0
                            this.grid[i][k] = 0;
                        }else{
                            //if not the far left element set equal to the element on the left
                            this.grid[i][k] = this.grid[i][k-1];
                            //and set the element on the left equal to 0
                            this.grid[i][k-1] = 0;
                        }
                    }
                }
            }
        }
    }


    boolean can_move()
    {
        for(int x = 0; x < this.grid.length; ++x)
        {
            for(int y = 0; y < this.grid[0].length; ++y)
            {
                // Only need 1 empty space to move
                if(this.grid[y][x] == 0)
                {
                    return true;
                }

                // Neighbor check
                int value = this.grid[y][x];

                int north = get(x, y - 1);
                if(value == north)
                {
                    return true;
                }

                int south = get(x, y + 1);
                if(value == south)
                {
                    return true;
                }

                int east = get(x - 1, y);
                if(value == east)
                {
                    return true;
                }

                int west = get(x + 1, y);
                if(value == west)
                {
                    return true;
                }
            }
        }

        // If we made it through, there are no empty slots or
        // slots with equal values next to it
        return false;
    }
     int get(int x, int y)
    {
        if(is_outside(x, y))
        {
            return -1;
        }

        return this.grid[y][x];
    }
}
    
