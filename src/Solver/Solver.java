package Solver;

import javax.swing.JPanel;

public class Solver {
	/**
	 * Solve Logic expression normalizer problem
	 * @param num_of_variable 
	 * @param truth_table array of 2^num_of_variables
	 * @param output_type "SOP" or "POS"
	 * @return 0 is written as 'A' and so on; if returned number >= num_of_variable, it is written as negative of its modulo of num_of_variable
	 */
	public static int[][] solve(Integer num_of_variable, int[] truth_table, String output_type, JPanel process_output){
		int[][] ret = {{2},{4,2},{0,1,5},{2,3,4,5}};
		return ret;
	}
}
