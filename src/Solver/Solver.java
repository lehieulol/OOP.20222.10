package Solver;

import javax.swing.JPanel;
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
		int t[] = {0,4,5,7,8,11,12,15};
		truth_table = t;
		QuineMcCluskey start = new QuineMcCluskey();
		int[][] ret = start.result(truth_table, num_of_variable);
//		System.out.println(truth_table.length);
		for(int i=0; i<ret.length; i++) {
			for(int j=0; j<ret[i].length; j++) {
				System.out.print(ret[i][j] + " ");
			}
			System.out.println();
		}
		return ret;
	}
}
