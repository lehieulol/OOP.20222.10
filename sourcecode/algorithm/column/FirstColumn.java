package algorithm.column;
import java.util.ArrayList;

import algorithm.MinTerm;

public class FirstColumn extends Column {

	private ArrayList<MinTerm> minTerms;
	private int numOfMinTerms;
	
	public FirstColumn(ArrayList<MinTerm> minTerms, int lengthOfBits){
		this.setMinTerms(minTerms);
		this.setLengthOfBits(lengthOfBits);
		this.setColumn(this.separate(minTerms));
		this.setNumOfMinTerms(minTerms.size());
	}
	
	
	public ArrayList<ArrayList<ArrayList<Integer>>> separate(ArrayList<MinTerm> list){
		ArrayList<ArrayList<ArrayList<Integer>>> result = new ArrayList<ArrayList<ArrayList<Integer>>>();
		for(int i = 0; i < this.lengthOfBits+1; i++) {
			result.add(new ArrayList<ArrayList<Integer>>());
		}
		
		int num;
		for(MinTerm minTerm: list) {
			num = minTerm.noOfOnes;
			result.get(num).add(minTerm.getMinTerms());
		}
		
		return result;
	}
	
	

	public ArrayList<MinTerm> getMinTerms() {
		return minTerms;
	}

	public void setMinTerms(ArrayList<MinTerm> minTerms) {
		this.minTerms = minTerms;
	}
	
	public static void main(String[] args){
		ArrayList<Integer> b = new ArrayList<Integer>();
		b.add(0);
		b.add(2);
		b.add(4);
		b.add(5);
		b.add(8);
		b.add(7);
		b.add(11);
		b.add(12);
		b.add(15);
		ArrayList<MinTerm> c = new ArrayList<MinTerm>();
		for(Integer k: b) {
			int i = k;
			c.add(new MinTerm(i,4));
		}
		FirstColumn a = new FirstColumn(c,4);
		System.out.println(a.getColumn());
	}


	public int getNumOfMinTerms() {
		return numOfMinTerms;
	}


	public void setNumOfMinTerms(int numOfMinTerms) {
		this.numOfMinTerms = numOfMinTerms;
	}
	
	
}
