package algorithm.column;
import java.util.ArrayList;

import algorithm.MinTerm;
public class IntermediateColumn extends Column {
	
	private ArrayList<ArrayList<Integer>> primeImplicants = new ArrayList<ArrayList<Integer>>();
	private ArrayList<ArrayList<Integer>> checkList = new ArrayList<ArrayList<Integer>>();
	
	public IntermediateColumn(ArrayList<ArrayList<ArrayList<Integer>>> column, int lengthOfBits){
		this.setLengthOfBits(lengthOfBits);
		this.generateCheckList(column);
		this.setColumn(this.step(column));
	}
	
	

	public ArrayList<ArrayList<ArrayList<Integer>>> step(ArrayList<ArrayList<ArrayList<Integer>>> list) {
		ArrayList<ArrayList<ArrayList<Integer>>> result = new ArrayList<ArrayList<ArrayList<Integer>>>();
		for(int i = 0; i < list.size()-1; i++) {
			result.add(new ArrayList<ArrayList<Integer>>()); //tao number of index 0 = prestep -1
		}
		
		ArrayList<Integer> temp;
		for(int i = 0; i < list.size()-1; i++) {
			for(int j = 0; j < list.get(i).size(); j++) {
				for(int k = 0; k < list.get(i+1).size(); k++) {
					temp = this.compare(list.get(i).get(j),list.get(i+1).get(k));
					if(temp.size()!=0) {
						result.get(i).add(temp);
						this.checkList.get(i).set(j, 1);
						this.checkList.get(i+1).set(k,1);
					}
				}
			}
		}
		for(int i=0; i < checkList.size(); i++) {
			for(int j=0; j<checkList.get(i).size();j++) {
				if(checkList.get(i).get(j)==0) {
					temp = new ArrayList<Integer>();
					for(int k= this.lengthOfBits; k<list.get(i).get(j).size(); k++) {
						if(temp.contains(list.get(i).get(j).get(k))) {
							
						}
						else temp.add(list.get(i).get(j).get(k));
					}
					this.primeImplicants.add(temp);
				}
			}
		}
		
		return result;
	}
	
	
	public void generateCheckList(ArrayList<ArrayList<ArrayList<Integer>>> list) {
		for(int i=0; i<list.size(); i++) {
			this.checkList.add(new ArrayList<Integer>());
			for(int j=0; j<list.get(i).size(); j++) {
				this.checkList.get(i).add(0);
			}
		}
	}
	
	public ArrayList<Integer> compare(ArrayList<Integer> a1, ArrayList<Integer> a2){
		ArrayList<Integer> result = new ArrayList<Integer>();
		for(Integer i: a1) {
			result.add(i);
		}
		int count = this.noOfNines(a1);
		int position = 0;
		for(int i = 0; i <this.lengthOfBits; i++) {
			if(a1.get(i)!=a2.get(i) && (a1.get(i)==9 || a2.get(i)==9)) return new ArrayList<Integer>();
			if(a1.get(i)!=a2.get(i) && (a1.get(i)==0 ||a1.get(i)==1 ) && (a2.get(i)==0||a2.get(i)==1)) {
				count ++;
				position = i;
			}
		}
		if(count == this.noOfNines(a1)+1) {
			result.set(position,9);
			for(int i = this.lengthOfBits; i < a2.size(); i++) {
				result.add(a2.get(i));
			}
			return result;
		}
		return new ArrayList<Integer>();
	}
	
	public int noOfOnes(ArrayList<Integer> num) {
		int count = 0;
		for (int i=0; i<lengthOfBits; i++) {
			if (num.get(i) == 1) {
				count ++;
			}
		}
		return count;
	}
	public int noOfNines(ArrayList<Integer> num) {
		int count = 0;
		for (int i=0; i<lengthOfBits; i++) {
			if (num.get(i) == 9) {
				count ++;
			}
		}
		return count;
	}
	
	public ArrayList<ArrayList<Integer>> getPrimeImplicants() {
		return primeImplicants;
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
		IntermediateColumn a1 = new IntermediateColumn(a.getColumn(),4);
		IntermediateColumn a2 = new IntermediateColumn(a1.getColumn(),4);
		IntermediateColumn a3 = new IntermediateColumn(a2.getColumn(),4);
		System.out.println(a1.getPrimeImplicants());
		System.out.println(a2.getPrimeImplicants());
		System.out.println(a3.getPrimeImplicants());
	}
	
	
	
}
