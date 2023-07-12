package Solver;

import javax.swing.JPanel;
import java.util.ArrayList;
import algorithm.QuineMcCluskey;

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
		QuineMcCluskey start = new QuineMcCluskey();
		ArrayList<ArrayList<Integer>> ret = start.result(truth_table, num_of_variable);
		ArrayList<ArrayList<Integer>> bit = new ArrayList<ArrayList<Integer>>() ;
		int[][] result = {{0,1,2},{0,1,3}};
		for(ArrayList<Integer>i: ret){
			bit.add(start.implicantsToBin(i));
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
} 