package algorithm.column;
import java.util.ArrayList;

import algorithm.MinTerm;
public class IntermediateColumn extends Column {
	
	private ArrayList<ArrayList<Integer>> primeImplicants = new ArrayList<ArrayList<Integer>>(); // to save prime implicants
	private ArrayList<ArrayList<Integer>> checkList = new ArrayList<ArrayList<Integer>>(); // to check prime implicants
	
	public IntermediateColumn(ArrayList<ArrayList<ArrayList<Integer>>> column, int lengthOfBits){
		this.setLengthOfBits(lengthOfBits);
		this.generateCheckList(column);
		this.setColumn(this.step(column));
	}
	
	

	public ArrayList<ArrayList<ArrayList<Integer>>> step(ArrayList<ArrayList<ArrayList<Integer>>> list) {
		ArrayList<ArrayList<ArrayList<Integer>>> result = new ArrayList<ArrayList<ArrayList<Integer>>>();
		for(int i = 0; i < list.size()-1; i++) {
			result.add(new ArrayList<ArrayList<Integer>>()); //create number of index 0 = prestep -1
		}
		
		ArrayList<Integer> temp;
		for(int i = 0; i < list.size()-1; i++) {
			for(int j = 0; j < list.get(i).size(); j++) {
				for(int k = 0; k < list.get(i+1).size(); k++) {
					temp = this.compare(list.get(i).get(j),list.get(i+1).get(k));
					if(temp.size()!=0) {
						result.get(i).add(temp);
						this.checkList.get(i).set(j, 1);
						this.checkList.get(i+1).set(k,1); //if can compare, set it to 1 to go to next step
					}
				}
			}
		}
		for(int i=0; i < checkList.size(); i++) {
			for(int j=0; j<checkList.get(i).size();j++) {
				if(checkList.get(i).get(j)==0) { //checklist remain 0
					temp = new ArrayList<Integer>();
					for(int k= this.lengthOfBits; k<list.get(i).get(j).size(); k++) {
						if(temp.contains(list.get(i).get(j).get(k))) {
							
						}
						else temp.add(list.get(i).get(j).get(k)); //if remains 0, so it is prime implicant
					}
					this.primeImplicants.add(temp);
				}
			}
		}
		
		return result;
	}
	
	
	public void generateCheckList(ArrayList<ArrayList<ArrayList<Integer>>> list) { //create checklist, set all to 0
		for(int i=0; i<list.size(); i++) {
			this.checkList.add(new ArrayList<Integer>());
			for(int j=0; j<list.get(i).size(); j++) {
				this.checkList.get(i).add(0);
			}
		}
	}
	
	public ArrayList<Integer> compare(ArrayList<Integer> a1, ArrayList<Integer> a2){ //compare 2 binary represent, if different in only 1 positions, can return
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
	public int noOfNines(ArrayList<Integer> num) { //number of dont care values in binary representation
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
	
	
	
	
	
}
