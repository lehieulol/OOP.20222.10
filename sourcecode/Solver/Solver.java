package Solver;

import algorithm.*;
import algorithm.column.FirstColumn;

import javax.swing.JPanel;
import java.util.ArrayList;

public class Solver {
	/**
	 * Solve Logic expression normalizer problem
	 * @param num_of_variable 
	 * @param truth_table array of 2^num_of_variables
	 * @param output_type "SOP" or "POS"
	 * @return 0 is written as 'A' and so on; if returned number >= num_of_variable, it is written as negative of its modulo of num_of_variable
	 */
	public static int[][] solve(Integer num_of_variable, int[] truth_table, String output_type, JPanel process_output){
//		int t[] = {1,2,3};
//		truth_table = t;
		
		ArrayList<Integer> mT = new ArrayList<Integer>();
		for (int i = 0; i< truth_table.length; i++) {
			mT.add(truth_table[i]);
		}
		
		ArrayList<MinTerm> r = new ArrayList<MinTerm>();
		for(Integer k: mT) {
			int i = k;
			r.add(new MinTerm(i, num_of_variable));
		}
		FirstColumn a = new FirstColumn(r,num_of_variable);
		TransformColumn aa = new TransformColumn(a, mT);
		ArrayList<ArrayList<Integer>>ret = aa.getCollectPrimeImplicants();
		System.out.println(ret);
		ArrayList<ArrayList<Integer>> bit = new ArrayList<ArrayList<Integer>>() ;
		int[][] result = {{0,1,2},{0,1,3}};
		for(ArrayList<Integer>i: ret){
			bit.add(implicantsToBin(i, num_of_variable));
		}
		if(output_type.equals("SOP")) {
			result = resultSOP(bit);
		}
		else if(output_type.equals("POS")) {
			result = resultPOS(bit);
		}
		
		return result;
	}
	public static int[][] resultSOP(ArrayList<ArrayList<Integer>> bit){
		
		ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> temp;
		for(ArrayList<Integer> i: bit) {
			temp = new ArrayList<Integer>();
			System.out.println(i);
			for(int j=0; j<i.size(); j++) {
				if(i.get(j)==1) {
					temp.add(j);
				}
				else if(i.get(j)==0) {
					temp.add(j+i.size());
				}
			}
			res.add(temp);
		}
		int[][] result = convert(res);
		return result;
		
	}
public static int[][] resultPOS(ArrayList<ArrayList<Integer>> bit){
		
		ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> temp;
		for(ArrayList<Integer> i: bit) {
			temp = new ArrayList<Integer>();
			System.out.println(i);
			for(int j=0; j<i.size(); j++) {
				if(i.get(j)==0) {
					temp.add(j);
				}
				else if(i.get(j)==1) {
					temp.add(j+i.size());
				}
			}
			res.add(temp);
		}
		ArrayList<ArrayList<Integer>> newTemp = new ArrayList<ArrayList<Integer>>();
		for(ArrayList<Integer> i: res) {
			newTemp.add(0,i);
		}
		int[][] result = convert(newTemp);
		return result;
		
	}
	public static int[][] convert(ArrayList<ArrayList<Integer>> arrayList) {
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
	public static ArrayList<Integer> implicantsToBin(ArrayList<Integer> implicants, int lengthOfBits){
		ArrayList<Integer> result = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> temp = convertMinTerms(implicants, lengthOfBits);
		int t;
		boolean dif;
		for(int j=0; j<lengthOfBits; j++) {
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
	public static ArrayList<ArrayList<Integer>> convertMinTerms(ArrayList<Integer> mint, int lengthOfBits) {
		int t;
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < mint.size(); i++) {
			result.add(new ArrayList<Integer>());
		}
		for (int i = 0; i < mint.size(); i++) {
			t = mint.get(i);
			result.get(i).add(t);
			for(int j = lengthOfBits; j>0; j--) {
				result.get(i).add(0, t%2);
				t = t/2;
			}
		}
		return result;
	}
	
} 
