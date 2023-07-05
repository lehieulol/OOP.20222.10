package ui.Input_Panel;


import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;

public class InputPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JPanel tmp = new JPanel();
	
	public InputPanel(int w, int h) {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setSize(w, h);
	}
	
	@SuppressWarnings("unchecked")
	public int[] getTruthTable(String output_type) {
		Component[] components = tmp.getComponents();
		ArrayList<Integer> tmp_ali = new ArrayList<Integer>();
		
		int i = 0;
		for (Component component : components) {
			if(component.getClass().isInstance(new JComboBox<Integer>())) {
				if (output_type.equals("SOP")) {
					if ((int) ((JComboBox<Integer>) component).getSelectedItem() == 1) {
						tmp_ali.add(i);
					}
				}else if(output_type.equals("POS")) {
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
	
	public void input_panel_generator(Integer NoV, String iT) {
		removeAll();
		
		JLabel prompt = new JLabel(iT);
		prompt.setAlignmentX(1.0f);
		add(prompt);
		tmp.removeAll();
		tmp.setAlignmentX(0.7f);
		add(tmp);
		tmp.setLayout(new GridBagLayout());
		tmp.setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(1,1,1,1);
		if(iT.equals("Truth table")) {
	        gbc.gridx = 1;
	        gbc.gridy = 0;
	        gbc.gridheight = 1;
	        gbc.gridwidth = NoV;
	        tmp.add(new JLabel("X", JLabel.CENTER), gbc);
	        
	        gbc.gridx = NoV+1;
	        gbc.gridy = 0;
	        gbc.gridheight = 2;
	        gbc.gridwidth = 1;
	        tmp.add(new JLabel("Y", JLabel.CENTER), gbc);
	        
	        
	        
	        gbc.gridheight = 1;
	        gbc.gridwidth = 1;
	        for (int _i = 0; _i < NoV; _i++) {
	        	gbc.gridy = 1;
	        	gbc.gridx = _i+1;
	        	tmp.add(new JLabel(String.valueOf((char)('A'+_i)), JLabel.CENTER), gbc);
	        }
	        
	        for (int _j = 0; _j < (int) Math.pow(2, NoV); _j++) {
	        	gbc.gridy = _j+2;
	        	gbc.gridx = 0;
	        	tmp.add(new JLabel(String.valueOf(_j), JLabel.CENTER), gbc);
	        	for (int _i = 0; _i < NoV; _i++) {
	            	gbc.gridx = _i+1;
	            	tmp.add(new JLabel(String.valueOf(_j/(int) Math.pow(2, NoV-_i-1)%2), JLabel.CENTER), gbc);
	        	}
	        	gbc.gridx = NoV+1;
	        	Integer[] tmp1 = {0, 1};
	        	tmp.add(new JComboBox<Integer>(tmp1), gbc);
	        }
		}else if(iT.equals("Kmap (SOP)")) {
	        gbc.gridx = 0;
	        int column_variables = (NoV+1)/2, row_variables = NoV-column_variables;
	        int convert[] = {1, 2, 4, 3};
	        for (int i = 0; i < (int) Math.pow(2, column_variables); i++) {
	        	String tmp1 = "";
	        	gbc.gridy = convert[i];
	        	for(int j = 0; j < column_variables; j++) {
	        		tmp1 += (char) ('A'+j);
	        		if(i/(int) Math.pow(2, j)%2==0) {
	        			tmp1 += '\u0305';
	        		}
	        	}
	        	JLabel temp = new JLabel(tmp1, JLabel.CENTER);
	        	tmp.add(temp, gbc);
	        }
	        gbc.gridy = 0;
	        for (int i = 0; i < (int) Math.pow(2, row_variables); i++) {
	        	String tmp1 = "";
	        	gbc.gridx = convert[i];
	        	for(int j = 0; j < row_variables; j++) {
	        		tmp1 += (char) ('A'+column_variables+j);
	        		if(i/(int) Math.pow(2, j)%2==0) {
	        			tmp1 += '\u0305';
	        		}
	        	}
	        	JLabel temp = new JLabel(tmp1, JLabel.CENTER);
	        	tmp.add(temp, gbc);
	        }
	        for (int i = 0; i < (int) Math.pow(2, column_variables); i++) {
	        	gbc.gridy = convert[i];
	        	for (int i1 = 0; i1 < (int) Math.pow(2, row_variables); i1++) {
		        	gbc.gridx = convert[i1];
	        		Integer[] tmp1 = {0, 1};
		        	tmp.add(new JComboBox<Integer>(tmp1), gbc);
	        	}
	        }
		}
		// Set Font
		for(Component cpn:tmp.getComponents()) {
        	((JComponent) cpn).setOpaque(true);
    		cpn.setFont(new Font("TimesRoman", Font.BOLD, 18));
        }
		tmp.setMaximumSize(tmp.getPreferredSize());
		
		revalidate();
	}
}
