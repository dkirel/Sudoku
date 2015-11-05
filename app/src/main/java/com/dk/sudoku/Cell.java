package com.dk.sudoku;

import java.util.ArrayList;


public class Cell implements Cloneable {
	
	private int value;
	private boolean given = false;
	private ArrayList<Integer> pencil = new ArrayList<Integer>();
	
	public Cell() {}
	
	public Cell(int val) {

		if (val < 0 || val > 9) {
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
		
		if (value < 0 || value > 9) {
			return false;
		}
		
		if (usePencil == false) {
			this.value = value;
			return true	;		
		}
		
		//Logic to add value
		if (this.value == value) {
			this.value = 0;
		}
		else if (this.value > 0 && this.value != value) {

			if (pencil.size() != 0)
				return false;
			
			pencil.add(this.value);
			pencil.add(value);
			this.value = 0;
		}
		else if (this.value == 0) {
			
			if (pencil.size() < 2)
				return false;
			
			if(pencil.contains(value)) {
				
				pencil.remove(new Integer(value));
				
				if (pencil.size() == 1) {
					this.value = pencil.get(0);
					pencil.remove(0);
				}
			}
			else {
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
	
}


