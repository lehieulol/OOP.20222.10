package algorithm;
import java.util.ArrayList;
import java.util.Collections;

import algorithm.column.FirstColumn;
import algorithm.column.IntermediateColumn;

public class TransformColumn {
	
	private ArrayList<ArrayList<Integer>> collectPrimeImplicants = new ArrayList<ArrayList<Integer>>(); //to save all prime implicants
	private int lengthOfBits;
	private int noOfMinTerms;
	private ArrayList<Integer> minTerms;
	
	public TransformColumn(FirstColumn firstColumn, ArrayList<Integer> minTerms){
		this.setLengthOfBits(firstColumn.getLengthOfBits());
		this.setNoOfMinTerms(firstColumn.getNumOfMinTerms());
		this.setMinTerms(minTerms);
		ArrayList<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer>>();
		
//		avoid first while
		temp.add(new ArrayList<Integer>());
//		ArrayList<Integer> to;
		IntermediateColumn interColumn0 = new IntermediateColumn(firstColumn.getColumn(), this.getLengthOfBits()); //column to save the prestep
		IntermediateColumn interColumn = new IntermediateColumn(firstColumn.getColumn(), this.getLengthOfBits()); //column to save the intermediate step
		int i = 0;
		while(i<6) {
//			temp = new ArrayList<ArrayList<Integer>>();
//			interColumn = new IntermediateColumn(interColumn0.getColumn(), this.getLengthOfBits());
//			interColumn0 = new IntermediateColumn(interColumn0.getColumn(), this.getLengthOfBits());
			
//			if(interColumn.getPrimeImplicants().size()==0 && i != 0) {
//				break;
//			}
			for(ArrayList<Integer> k: interColumn.getPrimeImplicants()) {
				Collections.sort(k);
				if(this.collectPrimeImplicants.contains(k)) {
					
				}
				else this.collectPrimeImplicants.add(k);
			}
//			System.out.println(1);
			interColumn = new IntermediateColumn(interColumn0.getColumn(), this.getLengthOfBits()); // intermediate step = next of prestep
			interColumn0 = new IntermediateColumn(interColumn0.getColumn(), this.getLengthOfBits()); // prestep moves on
			i++;
		}
		
		ArrayList<ArrayList<Integer>> k = new ArrayList<ArrayList<Integer>>();
		for (ArrayList<Integer> array: this.collectPrimeImplicants) {
			k.add(array);
		}
			
		this.collectPrimeImplicants = this.simplifyImplicants(k); // simplify prime implicants
		
	}
	
	
	public ArrayList<ArrayList<Integer>> simplifyImplicants(ArrayList<ArrayList<Integer>> implicants){ //simplify implicants to shorten the expression
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> noOfAppear = new ArrayList<ArrayList<Integer>>();
		int count;
		ArrayList<Integer> temp;
		for(int i=0; i<this.minTerms.size(); i++) {
			temp = new ArrayList<Integer>();
			count = 0;
			for(ArrayList<Integer> j : implicants) {
				if(j.contains(minTerms.get(i))){
					count++;
				}
			}
			temp.add(minTerms.get(i));
			temp.add(count);
			noOfAppear.add(temp);
		}
		Collections.sort(noOfAppear, (a, b)->a.get(1).compareTo(b.get(1))); //group of implicants with more minterms is prioritized
		
		ArrayList<Integer> s0 = new ArrayList<Integer>(); 
		ArrayList<Integer> sc = new ArrayList<Integer>(); 
		for(ArrayList<Integer> i: noOfAppear) {
			s0.add(i.get(0));
			sc.add(i.get(0));
		}
		
		for(Integer ii: s0) {
			if(sc.contains(ii)) {
				for(ArrayList<Integer> i: implicants) { //cai tien de tim dc implicant con nhieu so con thua nhat
					if(i.contains(ii)) {
						for(int k=0; k<i.size(); k++) {
							sc.remove(i.get(k));
						}
						result.add(i);
					}
				}
			}
		}
		
		ArrayList<ArrayList<Integer>> tempo = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> tempC = new ArrayList<ArrayList<Integer>>();
		for (ArrayList<Integer> i: result) {
			tempo.add(i);
			tempC.add(i);
		}
		result = new ArrayList<ArrayList<Integer>>();
		for(int i=0; i<tempo.size(); i++) {
			count = 0;
			for(int k=0; k<tempo.get(i).size(); k++) {
				for(int j =0; j<tempC.size(); j++) {
					if(tempo.get(i).equals(tempC.get(j))) {
						continue;
					}
					else {
						if(tempC.get(j).contains(tempo.get(i).get(k))) {
							count++;
							break;
						}
					}
				}
			}
			if(count==tempo.get(i).size()) {
				tempC.remove(tempo.get(i));
			}
			else result.add(tempo.get(i));
		}
		ArrayList<ArrayList<Integer>> newResult = new ArrayList<ArrayList<Integer>>();
		for(ArrayList<Integer> i: result) {
			if(newResult.contains(i)) continue;
			else {
				newResult.add(i);
			}
		}
		return newResult;
	}
	
	
	
	
	public int getLengthOfBits() {
		return lengthOfBits;
	}
	public void setLengthOfBits(int lengthOfBits) {
		this.lengthOfBits = lengthOfBits;
	}
	
	public static void main(String args[]) {
		ArrayList<Integer> b = new ArrayList<Integer>();
		b.add(0);
//		b.add(2);
//		b.add(1);
		b.add(4);
		b.add(5);
		b.add(8);
		b.add(7);
//		b.add(10);
		b.add(11);
		b.add(12);
		b.add(15);
		ArrayList<MinTerm> c = new ArrayList<MinTerm>();
		for(Integer k: b) {
			int i = k;
			c.add(new MinTerm(i,4));
		}
		FirstColumn a = new FirstColumn(c,4);
		TransformColumn aa = new TransformColumn(a,b);
		System.out.println(aa.collectPrimeImplicants);
		System.out.println(aa.simplifyImplicants(aa.collectPrimeImplicants));
	}


	public int getNoOfMinTerms() {
		return noOfMinTerms;
	}


	public void setNoOfMinTerms(int noOfMinTerms) {
		this.noOfMinTerms = noOfMinTerms;
	}


	public ArrayList<Integer> getMinTerms() {
		return minTerms;
	}


	public void setMinTerms(ArrayList<Integer> minTerms) {
		this.minTerms = minTerms;
	}


	public ArrayList<ArrayList<Integer>> getCollectPrimeImplicants() {
		return collectPrimeImplicants;
	}
}
