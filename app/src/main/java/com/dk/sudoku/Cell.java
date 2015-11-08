package com.dk.sudoku;

import java.util.ArrayList;


public class Cell implements Cloneable {

    private int x;
    private int y;
    private int value;
    private boolean given = false;
    private ArrayList<Integer> pencil = new ArrayList<Integer>();
    public static int CELL_VALUES = 9;

    public Cell() {
    }

    public Cell(int val) {

        if (val < 0 || val > CELL_VALUES) {
            value = 0;
        }

        value = val;

    }

    public Cell(int val, boolean given) {
        this(val);
        this.given = given;
    }

    public Cell(int val, boolean given, ArrayList<Integer> pencil) {

        this(val);
        this.given = given;
        this.pencil = (ArrayList<Integer>) pencil.clone();

    }

    public boolean setValue(int value, boolean usePencil) {

        if (value < 0 || value > CELL_VALUES) {
            return false;
        }

        if (usePencil == false) {
            this.value = value;
            return true;
        }

        //Logic to add value
        if (this.value == value) {
            this.value = 0;
        } else if (this.value == 0 && pencil.isEmpty()) {
            this.value = value;
        } else if (this.value > 0 && this.value != value) {

            if (pencil.size() != 0)
                return false;

            pencil.add(this.value);
            pencil.add(value);
            this.value = 0;
        } else if (this.value == 0) {

            //Must have more than 2 values in the pencil list for the pencil mode to exist
            if (pencil.size() == 1)
                return false;

            if (pencil.contains(value)) {

                pencil.remove(new Integer(value));

                if (pencil.size() == 1) {
                    this.value = pencil.get(0);
                    pencil.remove(0);
                }
            } else {
                pencil.add(value);
            }

        }


        return true;
    }

    public int getValue() {
        return value;
    }

    public ArrayList<Integer> getPencil() {
        return pencil;
    }

    public void setGiven(boolean given) {
        this.given = given;
    }

    public boolean isGiven() {
        return given;
    }

    public Object clone() {
        Cell cell = new Cell(this.value, this.given, this.pencil);
        return cell;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static int[] getPosition(int position) {
        int x = position / CELL_VALUES;
        int y = position % CELL_VALUES;

        int[] coordinates = {x, y};

        return coordinates;
    }

    public ArrayList<String> asArrayList() {

        int i;
        Integer integer;

        ArrayList<String> list = new ArrayList<String>();

        if (value > 0) {
            list.add(Integer.toString(value));
            return list;
        }
        else if (!pencil.isEmpty()) {
            for (i = 0; i < CELL_VALUES; i++) {

                integer = new Integer(i + 1);

                if (pencil.contains(integer))
                    list.add(integer.toString());
                else
                    list.add(" ");
            }
        }

        return list;

    }

}


