package ui;

import javax.swing.*;

import Solver.Solver;
import ui.Choice_Panel.ChoicePanel;
import ui.Input_Panel.InputPanel;
import ui.Output_Panel.OutputPanel;

import java.awt.*;

public class GUI_Frame extends JFrame{
	private static final long serialVersionUID = 1L;
	
	JPanel main_panel = new JPanel();
	
	ChoicePanel choice;
	InputPanel input;
	OutputPanel output;
	
	public GUI_Frame(String title){
		super(title);
		Container cp = getContentPane();
		
		main_panel.setLayout(new BorderLayout());
		cp.add(main_panel);
		
		choice = new ChoicePanel(this, getWidth(), getHeight()/8);
		main_panel.add(choice, BorderLayout.PAGE_START);
		
		input = new InputPanel(this.getWidth()/2, this.getHeight()*7/8);
		main_panel.add(input, BorderLayout.LINE_START);
		input.input_panel_generator(choice.getNoV(), choice.getInputType());
		
		output = new OutputPanel(this.getWidth()/2, this.getHeight()*7/8);
		main_panel.add(output, BorderLayout.LINE_END);
	}
	
	public void update_input(Integer NoV, String input_type) {
		input.input_panel_generator(NoV, input_type);
	}

	public void update_output(Integer NoV, String output_type) {
		int[] truth_table = input.getTruthTable(output_type);
		JPanel process = new JPanel();
		int[][] answer = Solver.solve(NoV, truth_table, output_type, process);
		int first_value = 0;
		if((output_type.equals("SOP")&&truth_table.length>0)||(output_type.equals("POS")&&truth_table.length==0)) {
			first_value = 1;
		}
		output.output_panel_generator(answer, NoV, output_type, process, first_value);
	}
	
}