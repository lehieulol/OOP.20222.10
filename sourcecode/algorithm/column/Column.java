package algorithm.column;
import java.util.ArrayList;

public class Column {
	//attribute of column
	protected int numOfColumn;
	protected int lengthOfBits;
	protected ArrayList<ArrayList<ArrayList<Integer>>> column;

	//getter and setter method
	public int getNumOfColumn() {
		return numOfColumn;
	}

	public void setNumOfColumn(int numOfColumn) {
		this.numOfColumn = numOfColumn;
	}

	public int getLengthOfBits() {
		return lengthOfBits;
	}

	public void setLengthOfBits(int lengthOfBits) {
		this.lengthOfBits = lengthOfBits;
	}

	public ArrayList<ArrayList<ArrayList<Integer>>> getColumn() {
		return column;
	}

	public void setColumn(ArrayList<ArrayList<ArrayList<Integer>>> column) {
		this.column = column;
	}
	
}
