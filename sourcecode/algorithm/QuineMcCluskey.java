package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;

public class QuineMcCluskey {

	private int lengthOfBits = 0;
	private int noOfMinTerms = 0;
	private ArrayList<Integer> minTerms = new ArrayList<Integer>() ;
	
	public ArrayList<ArrayList<Integer>> convertMinTerms(ArrayList<Integer> mint) {
		int t;
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < mint.size(); i++) {
			result.add(new ArrayList<Integer>());
		}
		for (int i = 0; i < mint.size(); i++) {
			t = mint.get(i);
			result.get(i).add(t);
			for(int j = this.lengthOfBits; j>0; j--) {
				result.get(i).add(0, t%2);
				t = t/2;
			}
		}
		return result;
	}
	public ArrayList<ArrayList<Integer>> convertMinTermsWithPrint(ArrayList<Integer> mint) {
		int t;
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < mint.size(); i++) {
			result.add(new ArrayList<Integer>());
		}
		for (int i = 0; i < mint.size(); i++) {
			t = mint.get(i);
			result.get(i).add(t);
			for(int j = this.lengthOfBits; j>0; j--) {
				result.get(i).add(0, t%2);
				t = t/2;
			}
		}
		for(ArrayList<Integer> i: result) {
			for(Integer ii: i) {
				System.out.print(ii);
			}
			System.out.println();
		}
		return result;
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
	
	public ArrayList<ArrayList<ArrayList<Integer>>> separate(ArrayList<ArrayList<Integer>> list){
		ArrayList<ArrayList<ArrayList<Integer>>> result = new ArrayList<ArrayList<ArrayList<Integer>>>();
		for(int i = 0; i < this.lengthOfBits+1; i++) {
			result.add(new ArrayList<ArrayList<Integer>>());
		}
		
		int num;
		for(ArrayList<Integer> array: list) {
			num = this.noOfOnes(array);
			result.get(num).add(array);
		}
		for(int i=0; i<result.size(); i++) {
			for(int j=0; j<result.get(i).size(); j++) {
				for(Integer ii: result.get(i).get(j)) {
					System.out.print(ii);
				}
				System.out.println();
			}
			System.out.println();
		}
		
		return result;
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
	
	public ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>> step(ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>> list) {
		ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>> result = new ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>>();
		result.add(new ArrayList<ArrayList<ArrayList<Integer>>>()); //index0
		for(int i = 0; i < list.get(0).size()-1; i++) {
			result.get(0).add(new ArrayList<ArrayList<Integer>>()); //tao number of index 0 = prestep -1
		}
		ArrayList<ArrayList<ArrayList<Integer>>> tem = new ArrayList<ArrayList<ArrayList<Integer>>>(); // tem la index1
		ArrayList<ArrayList<Integer>> t = new ArrayList<ArrayList<Integer>>();
		for(int i = 0; i < list.get(0).size(); i++) {
			t = new ArrayList<ArrayList<Integer>>();
			for(int j = 0; j < list.get(0).get(i).size(); j++) {
				t.add(new ArrayList<Integer>()); //remains size = 0 is prime implicant
			}
			tem.add(t);
		}
		result.add(tem);
		ArrayList<Integer> temp;
		for(int i = 0; i < list.get(0).size()-1; i++) {
			for(int j = 0; j < list.get(0).get(i).size(); j++) {
				for(int k = 0; k < list.get(0).get(i+1).size(); k++) {
					temp = this.compare(list.get(0).get(i).get(j),list.get(0).get(i+1).get(k));
					if(temp.size()!=0) {
						result.get(0).get(i).add(temp);
						result.get(1).get(i).get(j).add(1);
						result.get(1).get(i+1).get(k).add(1); //size != 0 is compared continuing
					}
				}
			}
		}
//		System.out.println("Index0");
//		for(int i =0; i < result.get(0).size(); i++) {
//			for(int j =0; j < result.get(0).get(i).size(); j++) {
//				for(Integer ii: result.get(0).get(i).get(j)) {
//					System.out.println(ii);
//				}
//				System.out.println();
//			}
//			System.out.println();
//		}
//		System.out.println("Index1");
//		for(int i =0; i < result.get(1).size(); i++) {
//			for(int j =0; j < result.get(1).get(i).size(); j++) {
//				for(Integer ii: result.get(1).get(i).get(j)) {
//					System.out.println(ii);
//				}
//				System.out.println();
//			}
//			System.out.println();
//		}
		return result;
	}
	
	public ArrayList<ArrayList<Integer>> primeImplicants(ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>> step,ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>> preStep){
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for(int i = 0 ; i < step.get(1).size(); i++) {
			for(int j = 0; j < step.get(1).get(i).size(); j++) {
				if(step.get(1).get(i).get(j).size()==0) {
					temp = new ArrayList<Integer>();
					for(int k = this.lengthOfBits; k < preStep.get(0).get(i).get(j).size(); k++) {
						if(temp.contains(preStep.get(0).get(i).get(j).get(k))) {
							
						}
						else {
							temp.add(preStep.get(0).get(i).get(j).get(k));
						}
					}
					result.add(temp);
				}
			}
		}
		return result;
	}
	public String binToString(ArrayList<Integer> bin) {
		String string = "";
		ArrayList<String> alphabet = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E"));
		for(int i=0; i< bin.size(); i++) {
			if(bin.get(i)==1) {
				string += alphabet.get(i);
			}
			else if(bin.get(i)==0) {
				string += alphabet.get(i)+"'";
			}
		}
		return string;
	}
	
	public ArrayList<Integer> numToBin(Integer implicant){
		ArrayList<Integer> result = new ArrayList<Integer>();
		int t = implicant;
		for(int j = this.lengthOfBits; j>0; j--) {
			result.add(0, t%2);
			t = t/2;
		}
		return result;
	}
	
	public ArrayList<Integer> implicantsToBin(ArrayList<Integer> implicants){
		ArrayList<Integer> result = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> temp = this.convertMinTerms(implicants);
		int t;
		boolean dif;
		for(int j=0; j<this.lengthOfBits; j++) {
			t = temp.get(0).get(j);
			dif = false;
			for(int i=1; i<temp.size(); i++) {
				if(t != temp.get(i).get(j)) {
					dif = true;
					break;
				}
			}
			if(dif) result.add(9);
			else result.add(t);
		}
		return result;
	}
	
	public ArrayList<ArrayList<Integer>> simplifyImplicants(ArrayList<ArrayList<Integer>> implicants){
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> noOfAppear = new ArrayList<ArrayList<Integer>>();
		int count;
		ArrayList<Integer> temp;
		for(int i=0; i<minTerms.size(); i++) {
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
		Collections.sort(noOfAppear, (a, b)->a.get(1).compareTo(b.get(1)));
		
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
		return result;
	}
	
	public ArrayList<ArrayList<Integer>> newSimplifyImplicants(ArrayList<ArrayList<Integer>> implicants){
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> tempo = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> tempC = new ArrayList<ArrayList<Integer>>();
		for (ArrayList<Integer> i: implicants) {
			tempo.add(this.implicantsToBin(i));
			tempC.add(this.implicantsToBin(i));
		}
		int count;
		for(int i=0; i<tempo.size(); i++) {
			count = 0;
			for(int k=0; k<this.lengthOfBits; k++) {
				if(tempo.get(i).get(k)==9) continue;
				for(int j =0; j<tempC.size(); j++) {
					if(tempo.get(i).equals(tempC.get(j))) {
						continue;
					}
					else {
						if(tempo.get(i).get(k)==tempC.get(j).get(k)) {
							count++;
							break;
						}
					}
				}
			}
			if(count==this.lengthOfBits - this.noOfNines(tempo.get(i))) {
				tempC.remove(tempo.get(i));
			}
			else result.add(tempo.get(i));
		}
		return result;
	}
	
	public ArrayList<ArrayList<Integer>> newNewSimplifyImplicants(ArrayList<ArrayList<Integer>> implicants){
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> noOfAppear = new ArrayList<ArrayList<Integer>>();
		int count;
		ArrayList<Integer> temp;
		for(int i=0; i<minTerms.size(); i++) {
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
		Collections.sort(noOfAppear, (a, b)->a.get(1).compareTo(b.get(1)));
		
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
			if(newResult.contains(this.implicantsToBin(i))) continue;
			else {
				newResult.add(this.implicantsToBin(i));
			}
		}
		return newResult;
	}
	public ArrayList<ArrayList<Integer>> newNewNewSimplifyImplicants(ArrayList<ArrayList<Integer>> implicants){
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> noOfAppear = new ArrayList<ArrayList<Integer>>();
		int count;
		ArrayList<Integer> temp;
		for(int i=0; i<minTerms.size(); i++) {
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
		Collections.sort(noOfAppear, (a, b)->a.get(1).compareTo(b.get(1)));
		
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
	
	public String conclusion(ArrayList<ArrayList<Integer>> implicants) {
		ArrayList<ArrayList<Integer>> simply = this.newNewSimplifyImplicants(implicants);
		System.out.println(simply.size());
		String result = this.binToString(simply.get(0));
		for(int i=1; i<simply.size(); i++) {
			result += " + " + this.binToString(simply.get(i));
		}
		return result;
	}
	
	
	
	
	public void main() { //int[] minterms, int lengthOfBits, int noOfMinTerms
//		ArrayList<Integer> mT = new ArrayList<Integer>();
//		for (int i = 0; i< noOfMinTerms; i++) {
//			mT.add(minterms[i]);
//		}
//		this.setMinTerms(mT);
//		this.setLengthOfBits(lengthOfBits);
//		this.setNoOfMinTerms(noOfMinTerms);
		
		System.out.println(noOfMinTerms);
		for(Integer i: this.minTerms) {
			System.out.println(i);
		}
		System.out.println("Convert minterms");
		ArrayList<ArrayList<Integer>> convert = this.convertMinTermsWithPrint(this.minTerms);
		System.out.println("Separate");
		ArrayList<ArrayList<ArrayList<Integer>>> first = this.separate(convert);
		ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>> tempo = new ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>>();
		tempo.add(first);
		ArrayList<ArrayList<ArrayList<Integer>>> tem = new ArrayList<ArrayList<ArrayList<Integer>>>();
		ArrayList<ArrayList<Integer>> t = new ArrayList<ArrayList<Integer>>();
		for(int i = 0; i < first.size(); i++) {
			for(int j = 0; j < first.get(i).size(); j++) {
				t.add(new ArrayList<Integer>()); //remains size = 0 is prime implicant
			}
			tem.add(t);
		}
		tempo.add(tem);
		ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>>second = this.step(tempo);
		ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>>third = this.step(second);
		ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>>forth = this.step(third);
		
		ArrayList<ArrayList<Integer>> implicants = new ArrayList<ArrayList<Integer>>();
		
		ArrayList<ArrayList<Integer>> tempImplicants = this.primeImplicants(third,second);
		for(ArrayList<Integer> i: tempImplicants) {
			implicants.add(i);
		}
		
		tempImplicants = this.primeImplicants(forth,third);
		for(ArrayList<Integer> i: tempImplicants) {
			Collections.sort(i);
			if(implicants.contains(i)) {
				break;
			}
			implicants.add(i);
		}
		
		System.out.println("Implicants: ");
		for(ArrayList<Integer> i: implicants) {
			for(Integer ii : i) {
				System.out.print(ii);
			}
			System.out.println();
		}
		String result = this.conclusion(implicants);
		System.out.println(result);
		System.out.println("End");
	}
	
	public ArrayList<ArrayList<Integer>> result(int[]minTerms, int lengthBits){
		ArrayList<Integer> mT = new ArrayList<Integer>();
		for (int i = 0; i< minTerms.length; i++) {
			mT.add(minTerms[i]);
		}
		this.setMinTerms(mT);
		this.setLengthOfBits(lengthBits);
		this.setNoOfMinTerms(minTerms.length);
		
		ArrayList<ArrayList<Integer>> convert = this.convertMinTermsWithPrint(this.minTerms);
		System.out.println("Separate");
		ArrayList<ArrayList<ArrayList<Integer>>> first = this.separate(convert);
		ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>> tempo = new ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>>();
		tempo.add(first);
		ArrayList<ArrayList<ArrayList<Integer>>> tem = new ArrayList<ArrayList<ArrayList<Integer>>>();
		ArrayList<ArrayList<Integer>> t = new ArrayList<ArrayList<Integer>>();
		for(int i = 0; i < first.size(); i++) {
			for(int j = 0; j < first.get(i).size(); j++) {
				t.add(new ArrayList<Integer>()); //remains size = 0 is prime implicant
			}
			tem.add(t);
		}
		tempo.add(tem);
		ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>>second = this.step(tempo);
		ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>>third = this.step(second);
		ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>>forth = this.step(third);
		ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>>fifth = this.step(forth);
		
		ArrayList<ArrayList<Integer>> implicants = new ArrayList<ArrayList<Integer>>();
		
		ArrayList<ArrayList<Integer>> tempImplicants = this.primeImplicants(second,tempo);
		for(ArrayList<Integer> i: tempImplicants) {
			Collections.sort(i);
			if(implicants.contains(i)) {
				continue;
			}
			implicants.add(i);
		}
		
		tempImplicants = this.primeImplicants(third,second);
		for(ArrayList<Integer> i: tempImplicants) {
			Collections.sort(i);
			if(implicants.contains(i)) {
				continue;
			}
			implicants.add(i);
		}
		
		tempImplicants = this.primeImplicants(forth,third);
		for(ArrayList<Integer> i: tempImplicants) {
			Collections.sort(i);
			if(implicants.contains(i)) {
				continue;
			}
			implicants.add(i);
		}
		
		tempImplicants = this.primeImplicants(fifth,forth);
		for(ArrayList<Integer> i: tempImplicants) {
			Collections.sort(i);
			if(implicants.contains(i)) {
				continue;
			}
			implicants.add(i);
		}
		
		ArrayList<ArrayList<Integer>> temp = this.newNewNewSimplifyImplicants(implicants);
		return temp;
	}
	
	public int[][] convert(ArrayList<ArrayList<Integer>> arrayList) {
		int[][] intArray = new int[arrayList.size()][];
		int k;
		for (int i = 0; i < arrayList.size(); i++) {
            ArrayList<Integer> subArrayList = arrayList.get(i);
            intArray[i] = new int[subArrayList.size()];
            
            for (int j = 0; j < subArrayList.size(); j++) {
            	k = subArrayList.get(j);
                intArray[i][j] = k;
            }
        }
		return intArray;
		
	  }

	public ArrayList<Integer> getMinTerms() {
		return minTerms;
	}

	public void setMinTerms(ArrayList<Integer> minTerms) {
		this.minTerms = minTerms;
	}

	public int getLengthOfBits() {
		return lengthOfBits;
	}

	public void setLengthOfBits(int lengthOfBits) {
		this.lengthOfBits = lengthOfBits;
	}

	public int getNoOfMinTerms() {
		return noOfMinTerms;
	}

	public void setNoOfMinTerms(int noOfMinTerms) {
		this.noOfMinTerms = noOfMinTerms;
	}
}
