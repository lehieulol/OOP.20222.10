package ui.Choice_Panel;

import java.awt.event.*;
import javax.swing.*;

import ui.GUI_Frame;

public class ChoicePanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JComboBox<Integer> num_variable;
	JComboBox<String> input_type;
	JComboBox<String> output_type;
	GUI_Frame main;
	
	public ChoicePanel(GUI_Frame main, int w, int h) {
		this.main = main;
		setSize(w, h);
		
		Integer[] no_of_variable = {3, 4};
		String[] _input_type = {"Truth table", "Kmap (SOP)"};
		String[] _output_type = {"SOP", "POS"};
		
		num_variable = new JComboBox<Integer>(no_of_variable);
		input_type = new JComboBox<String>(_input_type);
		output_type = new JComboBox<String>(_output_type);
		
		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		num_variable.addItemListener(new OptionChanged());
		input_type.addItemListener(new OptionChanged());
		JButton confirm = new JButton("Confirm");
		confirm.addActionListener(new ConfirmClicked());
		
		JLabel l1 = new JLabel("Number of variables:"), l2 = new JLabel("Input type:"), l3 = new JLabel("Output type:");
		int gap = 80;
		layout.setHorizontalGroup(
		   layout.createSequentialGroup()
		      .addGap(gap)
		      .addGroup(layout.createSequentialGroup()
		           .addComponent(l1)
		           .addComponent(num_variable))
		      .addGap(gap)
		      .addGroup(layout.createSequentialGroup()
		           .addComponent(l2)
		           .addComponent(input_type))
		      .addGap(gap)
		      .addGroup(layout.createSequentialGroup()
		           .addComponent(l3)
		           .addComponent(output_type))
		      .addGap(gap)
		      .addComponent(confirm)
		      .addGap(gap)
		);
		layout.setVerticalGroup(
			layout.createParallelGroup()
				.addComponent(l1)
				.addComponent(num_variable)
				.addComponent(l2)
				.addComponent(input_type)
				.addComponent(l3)
				.addComponent(output_type)
				.addComponent(confirm)
		);
	}
	
	private class OptionChanged implements ItemListener{
			
		@Override
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange() != ItemEvent.SELECTED) {
				return;
			}
			if(e.getSource() == num_variable || e.getSource() == input_type) {
				main.update_input(getNoV(), getInputType());
			}
		}
	}
	
	private class ConfirmClicked implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			main.update_output(getNoV(), getOutputType());
		}
	}
	
	public Integer getNoV() {
		return (Integer) num_variable.getSelectedItem();
	}
	
	public String getInputType() {
		return (String) input_type.getSelectedItem();
	}
	
	public String getOutputType() {
		return (String) output_type.getSelectedItem();
	}
}
