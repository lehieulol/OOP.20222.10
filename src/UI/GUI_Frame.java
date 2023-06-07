package UI;

import javax.swing.*;

import Solver.Solver;

import java.awt.*;
import java.awt.event.*;
import java.lang.Math.*;
import java.util.ArrayList;
import java.util.Arrays;

public class GUI_Frame extends JFrame{
	private static final long serialVersionUID = 1L;
	JPanel panel = new JPanel(), input_panel = new JPanel(), output_panel = new JPanel();
	JPanel choice_panel = new JPanel();
	Integer[] no_of_variable = {3, 4};
	JComboBox<Integer> num_variable = new JComboBox<Integer>(no_of_variable);
	String[] _output_type = {"SOP", "POS"};
	JComboBox<String> output_type = new JComboBox<String>(_output_type);
	
	public GUI_Frame(String title){
		super(title);
		this.setLayout(new BorderLayout());
		this.add(this.panel);
		
		this.choice_panel.setSize(this.getWidth(), this.getHeight()/8);
		this.panel.add(this.choice_panel, BorderLayout.PAGE_START);
		this.input_panel.setSize(this.getWidth()/2, this.getHeight()*7/8);
		this.panel.add(this.input_panel, BorderLayout.LINE_START);
		this.output_panel.setSize(this.getWidth()/2, this.getHeight()*7/8);
		this.panel.add(this.output_panel, BorderLayout.LINE_END);
		
		// Choice panel
		this.choice_panel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 0));
		this.choice_panel.add(new JLabel("Number of variables:"));
		num_variable.addItemListener(new OptionChanged(this));
		this.choice_panel.add(num_variable);
		
		this.choice_panel.add(new JLabel(""));
		this.choice_panel.add(new JLabel(""));
		
		this.choice_panel.add(new JLabel("Output type:"));
		this.choice_panel.add(output_type);

		this.choice_panel.add(new JLabel(""));
		this.choice_panel.add(new JLabel(""));
		
		JButton confirm = new JButton("Confirm");
		confirm.addActionListener(new ConfirmClicked(this));
		this.choice_panel.add(confirm);
		
		// Input panel
		this.input_panel.add(GUI_Frame.input_panel_generator((Integer) this.num_variable.getSelectedItem()));
	}
	
	// Choice panel event handler
	
	private class OptionChanged implements ItemListener{
		
		GUI_Frame frame;
		private OptionChanged(GUI_Frame frame) {
			this.frame = frame;
		}
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange() != ItemEvent.SELECTED) {
				return;
			}
			if(e.getSource() == this.frame.num_variable) {
				Integer NoV = (Integer) this.frame.num_variable.getSelectedItem();
				this.frame.input_panel.remove(0);
				this.frame.input_panel.add(input_panel_generator(NoV));
				this.frame.input_panel.revalidate();
			}
		}
	}
	
	private class ConfirmClicked implements ActionListener{
		GUI_Frame frame;
		private ConfirmClicked(GUI_Frame frame) {
			this.frame = frame;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			//Get number of variable:
			Integer NoV = (Integer) this.frame.num_variable.getSelectedItem();
			int[] truth_table = this.frame.getTruthTable();
			String output_type = (String) this.frame.output_type.getSelectedItem();
			Solver.solve(NoV, truth_table, output_type);
		}
	}
	
	// Input panel generator
	
	private static JPanel input_panel_generator(Integer NoV) {
		JPanel ret = new JPanel();
		ret.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = NoV;
        ret.add(new JLabel("X"), gbc);
        
        gbc.gridx = NoV;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.gridwidth = 1;
        ret.add(new JLabel("Y"), gbc);
        
        if (NoV > 4 || NoV <= 0) {
        	return null;
        }
        
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        for (int _i = 0; _i < NoV; _i++) {
        	gbc.gridy = 1;
        	gbc.gridx = _i;
        	ret.add(new JLabel(String.valueOf((char)('A'+_i))), gbc);
        }
        
        for (int _j = 0; _j < (int) Math.pow(2, NoV); _j++) {
        	gbc.gridy = _j+2;
        	for (int _i = 0; _i < NoV; _i++) {
            	gbc.gridx = _i;
            	ret.add(new JLabel(String.valueOf(_j/(int) Math.pow(2, NoV-_i-1)%2)), gbc);
        	}
        	gbc.gridx = NoV;
        	Integer[] tmp = {0, 1};
        	ret.add(new JComboBox<Integer>(tmp), gbc);
        }
        
        return ret;
	}

	public int[] getTruthTable() {
		Component[] components = ((Container) this.input_panel.getComponents()[0]).getComponents();
		int NoV = (int) this.num_variable.getSelectedItem();
		int[] ret = new int[(int) Math.pow(2, NoV)];
		int i = 0;
		for (Component component : components) {
			if(component.getClass().isInstance(new JComboBox<Integer>())) {
				ret[i++] = (int) ((JComboBox<Integer>) component).getSelectedItem();
			}
		}
		return ret;
	}
}
