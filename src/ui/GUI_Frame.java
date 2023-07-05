package ui;

import javax.swing.*;

import Solver.Solver;
import ui.Output_Panel.AND_Shape;
import ui.Output_Panel.OR_Shape;
import ui.Output_Panel.OutputPanel;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.ArrayList;
import java.awt.geom.*;

public class GUI_Frame extends JFrame{
	private static final long serialVersionUID = 1L;
	OutputPanel output = new OutputPanel();
	JPanel panel = new JPanel(), input_panel = new JPanel();
	JPanel choice_panel = new JPanel();
	Integer[] no_of_variable = {3, 4};
	JComboBox<Integer> num_variable = new JComboBox<Integer>(no_of_variable);
	String[] _input_type = {"Truth table", "Kmap (SOP)"};
	JComboBox<String> input_type = new JComboBox<String>(_input_type);
	String[] _output_type = {"SOP", "POS"};
	JComboBox<String> output_type = new JComboBox<String>(_output_type);
	
	public GUI_Frame(String title){
		super(title);
		Container cp = getContentPane();
		
		this.panel.setLayout(new BorderLayout());
		cp.add(this.panel);
		
		this.choice_panel.setSize(this.getWidth(), this.getHeight()/8);
		this.panel.add(this.choice_panel, BorderLayout.PAGE_START);
		
		
		this.input_panel.setSize(this.getWidth()/2, this.getHeight()*7/8);
		this.panel.add(this.input_panel, BorderLayout.LINE_START);
		
		
		this.output.setSize(this.getWidth()/2, this.getHeight()*7/8);
		this.panel.add(this.output, BorderLayout.LINE_END);
		
		// Test
		
		// Choice panel
		this.choice_panel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 0));
		this.choice_panel.add(new JLabel("Number of variables:"));
		num_variable.addItemListener(new OptionChanged());
		this.choice_panel.add(num_variable);
		
		this.choice_panel.add(new JLabel(""));
		this.choice_panel.add(new JLabel(""));
		
		this.choice_panel.add(new JLabel("Input type:"));
		input_type.addItemListener(new OptionChanged());
		this.choice_panel.add(input_type);
		
		this.choice_panel.add(new JLabel(""));
		this.choice_panel.add(new JLabel(""));
		
		this.choice_panel.add(new JLabel("Output type:"));
		this.choice_panel.add(output_type);

		this.choice_panel.add(new JLabel(""));
		this.choice_panel.add(new JLabel(""));
		
		JButton confirm = new JButton("Confirm");
		confirm.addActionListener(new ConfirmClicked());
		this.choice_panel.add(confirm);
		
		// Input panel
		this.input_panel.add(GUI_Frame.input_panel_generator(this.getNoV(), this.getInputType()));
		
	}
	
	// Getter
	public Integer getNoV() {
		return (Integer) this.num_variable.getSelectedItem();
	}
	
	public String getInputType() {
		return (String) this.input_type.getSelectedItem();
	}
	
	public String getOutputType() {
		return (String) this.output_type.getSelectedItem();
	}
	
	@SuppressWarnings("unchecked")
	public int[] getTruthTable() {
		Component[] components = ((Container) this.input_panel.getComponents()[0]).getComponents();
		ArrayList<Integer> tmp_ali = new ArrayList<Integer>();
		
		int i = 0;
		for (Component component : components) {
			if(component.getClass().isInstance(new JComboBox<Integer>())) {
				if (getOutputType().equals("SOP")) {
					if ((int) ((JComboBox<Integer>) component).getSelectedItem() == 1) {
						tmp_ali.add(i);
					}
				}else if(getOutputType().equals("POS")) {
					if ((int) ((JComboBox<Integer>) component).getSelectedItem() == 0) {
						tmp_ali.add(i);
					}
				}
				
				i++;
			}
		}
		int[] ret = new int[tmp_ali.size()];
		for (int i1 = 0; i1 < ret.length; i1++) {
			ret[i1] = tmp_ali.get(i1);
		}
		return ret;
	}
	
	// Choice panel event handler
	
	private class OptionChanged implements ItemListener{
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange() != ItemEvent.SELECTED) {
				return;
			}
			if(e.getSource() == num_variable || e.getSource() == input_type) {
				Integer NoV = getNoV();
				String iT = getInputType();
				input_panel.remove(0);
				input_panel.add(input_panel_generator(NoV, iT));
				input_panel.revalidate();
			}
		}
	}
	
	private class ConfirmClicked implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			//Get number of variable:
			Integer NoV = getNoV();
			int[] truth_table = getTruthTable();
			String output_type = getOutputType();
			JPanel process = new JPanel();
			int[][] answer = Solver.solve(NoV, truth_table, output_type, process);
			output_panel.removeAll();
			output_panel.add(process);
			output_panel.add(output_panel_generator(answer, NoV, output_type));
			output_panel.revalidate();
			
		}
	}
	
	// Input panel generator
	
	private static JPanel input_panel_generator(Integer NoV, String iT) {
		JPanel ret = new JPanel();
		ret.setLayout(new GridBagLayout());
		ret.setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(1,1,1,1);
		if(iT.equals("Truth table")) {
	        gbc.gridx = 1;
	        gbc.gridy = 0;
	        gbc.gridheight = 1;
	        gbc.gridwidth = NoV;
	        ret.add(new JLabel("X", JLabel.CENTER), gbc);
	        
	        gbc.gridx = NoV+1;
	        gbc.gridy = 0;
	        gbc.gridheight = 2;
	        gbc.gridwidth = 1;
	        ret.add(new JLabel("Y", JLabel.CENTER), gbc);
	        
	        if (NoV > 4 || NoV <= 0) {
	        	return null;
	        }
	        
	        gbc.gridheight = 1;
	        gbc.gridwidth = 1;
	        for (int _i = 0; _i < NoV; _i++) {
	        	gbc.gridy = 1;
	        	gbc.gridx = _i+1;
	        	ret.add(new JLabel(String.valueOf((char)('A'+_i)), JLabel.CENTER), gbc);
	        }
	        
	        for (int _j = 0; _j < (int) Math.pow(2, NoV); _j++) {
	        	gbc.gridy = _j+2;
	        	gbc.gridx = 0;
	        	ret.add(new JLabel(String.valueOf(_j), JLabel.CENTER), gbc);
	        	for (int _i = 0; _i < NoV; _i++) {
	            	gbc.gridx = _i+1;
	            	ret.add(new JLabel(String.valueOf(_j/(int) Math.pow(2, NoV-_i-1)%2), JLabel.CENTER), gbc);
	        	}
	        	gbc.gridx = NoV+1;
	        	Integer[] tmp = {0, 1};
	        	ret.add(new JComboBox<Integer>(tmp), gbc);
	        }
		}else if(iT.equals("Kmap (SOP)")) {
	        gbc.gridx = 0;
	        int column_variables = (NoV+1)/2, row_variables = NoV-column_variables;
	        int convert[] = {1, 2, 4, 3};
	        for (int i = 0; i < (int) Math.pow(2, column_variables); i++) {
	        	String tmp = "";
	        	gbc.gridy = convert[i];
	        	for(int j = 0; j < column_variables; j++) {
	        		tmp += (char) ('A'+j);
	        		if(i/(int) Math.pow(2, j)%2==0) {
	        			tmp += '\u0305';
	        		}
	        	}
	        	JLabel temp = new JLabel(tmp, JLabel.CENTER);
	        	ret.add(temp, gbc);
	        }
	        gbc.gridy = 0;
	        for (int i = 0; i < (int) Math.pow(2, row_variables); i++) {
	        	String tmp = "";
	        	gbc.gridx = convert[i];
	        	for(int j = 0; j < row_variables; j++) {
	        		tmp += (char) ('A'+column_variables+j);
	        		if(i/(int) Math.pow(2, j)%2==0) {
	        			tmp += '\u0305';
	        		}
	        	}
	        	JLabel temp = new JLabel(tmp, JLabel.CENTER);
	        	ret.add(temp, gbc);
	        }
	        for (int i = 0; i < (int) Math.pow(2, column_variables); i++) {
	        	gbc.gridy = convert[i];
	        	for (int i1 = 0; i1 < (int) Math.pow(2, row_variables); i1++) {
		        	gbc.gridx = convert[i1];
	        		Integer[] tmp = {0, 1};
		        	ret.add(new JComboBox<Integer>(tmp), gbc);
	        	}
	        }
		}
		// Set Font
		for(Component cpn:ret.getComponents()) {
        	((JComponent) cpn).setOpaque(true);
    		cpn.setFont(new Font("TimesRoman", Font.BOLD, 18));
        }
		
        return ret;
	}	
}