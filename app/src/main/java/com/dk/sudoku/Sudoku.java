package com.dk.sudoku;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Sudoku {

    private Cell[][] grid = new Cell[9][9];
    private Cell[][] solutionGrid = new Cell[9][9];

    private static final double MEDIAN = 4.5;
    public static final int EASY_GIVENS = 40;
    public static final int MEDIUM_GIVENS = 30;
    public static final int HARD_GIVENS = 25;
    public static final int EXPERT_GIVENS = 20;

    /*
     * Constructor for Sudoku objects with a specified solution
     */
    public Sudoku(Cell[][] grid, int givens) throws Exception {

        this.solutionGrid = grid;
        refreshGrid();

        if (isLegal(true) == false)
            throw new IOException("The grid provided is not complete and/or legal");

        generateGame(givens);

    }

    /*
     * Constructor for Sudoku objects without a specified solution
     */
    public Sudoku(InputStream is, int givens) throws Exception {

        loadStoredSolution(is);
        refreshGrid();

        if (isLegal(true) == false)
            throw new IOException("The stored solution grid is not complete and/or legal");

        //Generates Sudoku grid
        generateGame(givens);
    }

    /*
     * Constructor for Sudoku objects without a specified solution or givens
     */
    public Sudoku(InputStream is) throws Exception {

        loadStoredSolution(is);
        refreshGrid();

        if (isLegal(true) == false)
            throw new IOException("The stored solution grid is not complete and/or legal");

        //Generates Sudoku grid with easy amount of givens
        generateGame(EASY_GIVENS);
    }

    /*
     * Creates solutionGrid from existing file
     */
    private void loadStoredSolution(InputStream is) throws Exception {

        int i, j;
        String s;
        String[] parts;
        BufferedReader br = null;

        try {

            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            j = 0;

            while ((s = br.readLine()) != null) {
                parts = s.split("\t");

                if (parts.length != 9) {
                    throw new IOException("Incorrect number of rows");
                }

                for (i = 0; i < parts.length; i++) {
                    solutionGrid[i][j] = new Cell(Integer.parseInt(parts[i]));
                }

                j++;
            }

            if (j != 9) {
                throw new IOException("Incorrect number of rows");
            }
        } finally {
            br.close();
        }

    }

    /*
     * Populates grid with current state of solutionGrid
     */
    private void refreshGrid() {

        int i, j;

        for (i = 0; i < solutionGrid.length; i++) {
            for (j = 0; j < solutionGrid[0].length; j++) {
                grid[i][j] = (Cell) solutionGrid[i][j].clone();
            }
        }
    }

    /*
     * Generates full board
     */
    private void generateGame(int givens) {

        int rand, x, y, i, j;

        //Exchanges digits 0 - 10 times
        rand = (int) (10 * Math.random());

        for (i = 0; i < rand; i++) {

            x = 1 + (int) (9 * Math.random());
            y = 1 + (int) (9 * Math.random());

            exchangeDigits(x, y);
        }

        //Rotates grid by 0, 90, 180 or 270 degrees
        rand = (int) (4 * Math.random());
        rotateGrid(rand);

        //Swap rows in a band

        //Remove digits from solution
        removeDigits(givens);

        //Set position for each cell to know it's position
        for (i = 0; i < grid.length; i++) {
            for (j = 0; j < grid[0].length; j++) {
                grid[i][j].setPosition(i, j);
            }
        }

    }


    /*
     * Exchange two digits in a grid (x and y in this case)
     */
    private void exchangeDigits(int x, int y) {

        int i, j;

        for (i = 0; i < grid.length; i++) {
            for (j = 0; j < grid[0].length; j++) {

                if (grid[i][j].getValue() == x) {
                    grid[i][j].setValue(y, false);
                } else if (grid[i][j].getValue() == y) {
                    grid[i][j].setValue(x, false);
                }

            }
        }

    }


    /*
     * Rotate grid by 0, 90, 180 or 270 degrees clockwise
     */
    private boolean rotateGrid(int rotation) {

        //No rotation
        if (rotation == 0) {
            return true;
        }

        //90-degree rotation
        else if (rotation == 1) {
            transposeGrid();
            swapRows();
        }

        //180-degree rotation
        else if (rotation == 2) {
            transposeGrid();
        }

        //270-degree rotation
        else if (rotation == 3) {
            swapRows();
            transposeGrid();
        }

        //Incorrect output
        else {
            return false;
        }

        return true;
    }

    /*
     * Transpose the grid
     */
    private void transposeGrid() {

        int i, j;

        for (i = 0; i < grid.length; i++) {
            for (j = i; j < grid[0].length; j++) {
                exchangeCells(i, j, j, i);
            }
        }
    }

    /*
     * Swap rows of the grid
     */
    private void swapRows() {

        int i, j;

        for (i = 0, j = grid.length - 1; i < j; ++i, --j) {
            Cell[] x = grid[i];
            grid[i] = grid[j];
            grid[j] = x;
        }
    }

    /*
     * Exchange two cells in the grid
     */
    private void exchangeCells(int x1, int y1, int x2, int y2) {

        Cell tempCell = (Cell) grid[x1][y1].clone();

        grid[x1][y1] = (Cell) grid[x2][y2].clone();
        grid[x2][y2] = tempCell;

    }

    /*
     * Removes digits from full grid to create game grid
     */
    private boolean removeDigits(int targetGivens) {

        int i, j, currGivens;
        currGivens = 0;

        //Counts current givens
        for (i = 0; i < grid.length; i++) {
            for (j = 0; j < grid[0].length; j++) {
                if (grid[i][j].getValue() > 0)
                    currGivens++;
            }
        }

        if (currGivens != 81)
            return false;

        //Removes givens until targetGivens is acquired
        while (currGivens > targetGivens) {
            i = (int) (9 * Math.random());
            j = (int) (9 * Math.random());

            if (grid[i][j].getValue() != 0) {
                grid[i][j].setValue(0, false);
                currGivens--;
            }
        }

        return true;
    }

    /*
     * Makes sure the current grid is legal
     */
    private boolean isLegal(boolean completeCheck) {

        int i, j;

        for (i = 0; i < grid.length; i++) {
            for (j = 0; j < grid[0].length; j++) {

                if (completeCheck == true && grid[i][j].getValue() == 0) {
                    //System.out.println(String.format("X: %d; Y: %d", i, j));
                    return false;
                }

                if (isLegal(i, j) == false) {
                    //System.out.println(String.format("X: %d; Y: %d", i, j));
                    return false;
                }
            }

        }

        return true;

    }

    /*
     * Checks a given cell to see if it is legal in the current grid
     */
    private boolean isLegal(int x, int y) {

        int i, j, xMin, xMax, yMin, yMax, cellVal;

        xMin = 3 * ((int) x / 3);
        xMax = 3 * ((int) x / 3) + 2;
        yMin = 3 * ((int) y / 3);
        yMax = 3 * ((int) y / 3) + 2;
        cellVal = grid[x][y].getValue();

        //True if value is 0 (empty)
        if (grid[x][y].getValue() == 0) {
            return true;
        }

        //Check box
        for (i = xMin; i < xMax; i++) {
            for (j = yMin; j < yMax; j++) {
                if (grid[i][j].getValue() == cellVal && (x != i || y != j))
                    return false;
            }
        }

        //Check horizontal line
        for (i = 0; i < grid.length; i++) {
            if (grid[i][y].getValue() == cellVal && x != i)
                return false;
        }

        //Check vertical line
        for (j = 0; j < grid.length; j++) {
            if (grid[x][j].getValue() == cellVal && y != j)
                return false;
        }

        return true;

    }

    /*
     * Prints Sudoku game information
     */
    public String toString() {

        return String.format("Game Grid:\n%s\nSolution Grid:\n%s", gridString(grid), gridString(solutionGrid));
    }

    public String gridString(Cell[][] grid) {
        int i, j;
        String s;

        s = "";

        for (i = 0; i < grid.length; i++) {

            for (j = 0; j < grid[0].length; j++) {
                s += String.valueOf(grid[i][j].getValue()) + "\t";
            }

            s += "\n";
        }

        return s;
    }

    public ArrayList<Cell> asArrayList() {

        int i, j;
        ArrayList<Cell> list = new ArrayList<Cell>();

        for (i = 0; i < grid.length; i++) {
            list.addAll(Arrays.asList(grid[i]));
        }

        return list;
    }

    public ArrayList<String> asStringList() {

        int value;
        ArrayList<Cell> cellList;
        ArrayList<String> stringList;

        cellList = asArrayList();
        stringList = new ArrayList<String>();

        for(Cell cell: cellList) {

            value = cell.getValue();

            if (value == 0)
                stringList.add("");
            else
                stringList.add(Integer.toString(cell.getValue()));
        }

        return stringList;
    }

    public Cell getCell(int position) {

        if (position < 0)
            return null;

        int i, j;

        i = position/Cell.CELL_VALUES;
        j = position % Cell.CELL_VALUES;

        return grid[i][j];
    }

    public Cell getCell(int x, int y) {
        return grid[x][y];
    }

    public static int[] getCoordinates(int position) {

        int i, j;

        i = position/Cell.CELL_VALUES;
        j = position % Cell.CELL_VALUES;

        int[] result = {i, j};
        return result;

    }

    public void setCell(int position, int value) {
        int[] coordinates = getCoordinates(position);
        setCell(coordinates[0], coordinates[1], value);
    }

    public void setCell(int x, int y, int value) {
        grid[x][y].setValue(value, true);
    }

}
