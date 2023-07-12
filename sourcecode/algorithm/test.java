package algorithm;
import java.util.ArrayList;
//import java.util.Collections;
public class test {
	public static int[] minTerms = {0,4,5,7,8,11,12,15};
	public static  int noOfMinTerms = 8;
	public static int lengthOfBits = 4;
	
	public static void main(String[] args) {
		QuineMcCluskey start = new QuineMcCluskey();
		ArrayList<Integer> mT = new ArrayList<Integer>();
		for (int i = 0; i< noOfMinTerms; i++) {
			mT.add(minTerms[i]);
		}
		start.setMinTerms(mT);
		start.setLengthOfBits(lengthOfBits);
		start.setNoOfMinTerms(noOfMinTerms);
		start.main();
//		System.out.println(noOfMinTerms);
//		for(Integer i: start.minTerms) {
//			System.out.println(i);
//		}
//		System.out.println("Convert minterms");
//		ArrayList<ArrayList<Integer>> convert = start.convertMinTermsWithPrint(mT);
//		System.out.println("Separate");
//		ArrayList<ArrayList<ArrayList<Integer>>> first = start.separate(convert);
//		ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>> tempo = new ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>>();
//		tempo.add(first);
//		ArrayList<ArrayList<ArrayList<Integer>>> tem = new ArrayList<ArrayList<ArrayList<Integer>>>();
//		ArrayList<ArrayList<Integer>> t = new ArrayList<ArrayList<Integer>>();
//		for(int i = 0; i < first.size(); i++) {
//			for(int j = 0; j < first.get(i).size(); j++) {
//				t.add(new ArrayList<Integer>()); //remains size = 0 is prime implicant
//			}
//			tem.add(t);
//		}
//		tempo.add(tem);
//		ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>>second = start.step(tempo);
//		ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>>third = start.step(second);
//		ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>>forth = start.step(third);
//		
//		ArrayList<ArrayList<Integer>> implicants = new ArrayList<ArrayList<Integer>>();
//		
//		ArrayList<ArrayList<Integer>> tempImplicants = start.primeImplicants(third,second);
//		for(ArrayList<Integer> i: tempImplicants) {
//			implicants.add(i);
//		}
//		
//		tempImplicants = start.primeImplicants(forth,third);
//		for(ArrayList<Integer> i: tempImplicants) {
//			Collections.sort(i);
//			if(implicants.contains(i)) {
//				break;
//			}
//			implicants.add(i);
//		}
//		
//		System.out.println("Implicants: ");
//		for(ArrayList<Integer> i: implicants) {
//			for(Integer ii : i) {
//				System.out.print(ii);
//			}
//			System.out.println();
//		}
//		String result = start.conclusion(implicants);
//		System.out.println(result);
//		System.out.println("End");
	}
}