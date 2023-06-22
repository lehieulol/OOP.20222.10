package UI;

import javax.swing.*;

import Solver.Solver;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.geom.*;

public class GUI_Frame extends JFrame{
	private static final long serialVersionUID = 1L;
	JPanel panel = new JPanel(), input_panel = new JPanel(), output_panel = new JPanel();
	JPanel choice_panel = new JPanel();
	Integer[] no_of_variable = {3, 4};
	JComboBox<Integer> num_variable = new JComboBox<Integer>(no_of_variable);
	String[] _input_type = {"Truth table", "Kmap (SOP)"};
	JComboBox<String> input_type = new JComboBox<String>(_input_type);
	String[] _output_type = {"SOP", "POS"};
	JComboBox<String> output_type = new JComboBox<String>(_output_type);
	
	public GUI_Frame(String title){
		super(title);
		this.panel.setLayout(new BorderLayout());
		this.add(this.panel);
		
		this.choice_panel.setSize(this.getWidth(), this.getHeight()/8);
		this.panel.add(this.choice_panel, BorderLayout.PAGE_START);
		this.input_panel.setSize(this.getWidth()/2, this.getHeight()*7/8);
		this.panel.add(this.input_panel, BorderLayout.LINE_START);
		this.output_panel.setSize(this.getWidth()/2, this.getHeight()*7/8);
		this.panel.add(this.output_panel, BorderLayout.LINE_END);
		
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
	        gbc.gridx = 0;
	        gbc.gridy = 0;
	        gbc.gridheight = 1;
	        gbc.gridwidth = NoV;
	        ret.add(new JLabel("X", JLabel.CENTER), gbc);
	        
	        gbc.gridx = NoV;
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
	        	gbc.gridx = _i;
	        	ret.add(new JLabel(String.valueOf((char)('A'+_i)), JLabel.CENTER), gbc);
	        }
	        
	        for (int _j = 0; _j < (int) Math.pow(2, NoV); _j++) {
	        	gbc.gridy = _j+2;
	        	for (int _i = 0; _i < NoV; _i++) {
	            	gbc.gridx = _i;
	            	ret.add(new JLabel(String.valueOf(_j/(int) Math.pow(2, NoV-_i-1)%2), JLabel.CENTER), gbc);
	        	}
	        	gbc.gridx = NoV;
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
	
	// Output panel generator
	private JPanel output_panel_generator(int[][] answer, Integer NoV, String output_type) {
		JPanel ret = new JPanel();
		ret.setLayout(new BoxLayout(ret, BoxLayout.Y_AXIS));
		ret.add(new JLabel("Output:"));
		StringBuilder sb = new StringBuilder();
		if(output_type.equals("SOP")) {
			for (int[] _i : answer) {
				for (int _j :_i) {
					sb.append(String.valueOf((char) ('A'+_j%NoV)));
					if(_j>=NoV) {
						sb.append('\u0305');
					}
				}
				sb.append(" + ");	
			}
			sb.delete(sb.length() - 3, sb.length() - 1);
		}else if(output_type.equals("POS")) {
			for (int[] _i : answer) {
				sb.append("(");
				for (int _j :_i) {
					sb.append(String.valueOf((char) ('A'+_j%NoV)));
					if(_j>=NoV) {
						sb.append('\u0305');
					}
					sb.append(String.valueOf(" + "));
				}
				sb.delete(sb.length() - 3, sb.length() - 1);
				sb.append(")");	
			}
		}
		JTextField tmp = new JTextField(sb.toString());
		tmp.setFont(new Font("TimesRoman", Font.BOLD, 20));
		ret.add(tmp);
		ret.add(new JLabel(new ImageIcon(draw_logic_circuit(answer, NoV, output_type))));
		return ret;
	}
	
	private BufferedImage draw_logic_circuit(int[][] answer, Integer NoV, String output_type) {		
		double DISTANCE_BETWEEN_COLUMN = 25;
		double DISTANCE_BETWEEN_ROW = 25, DISTANCE_BETWEEN_ROW_GROUP = 60, DOT_SIZE = 7, ARROWHEAD_SIZEX = 12, ARROWHEAD_SIZEY = 7;
		
		double imageHeight = 0, imageWidth = 0;
		imageHeight += 150;
		imageHeight += (answer.length-1)*DISTANCE_BETWEEN_ROW_GROUP;
		for (int[] i:answer) {
			imageHeight += (i.length-1)*DISTANCE_BETWEEN_ROW;
		}
		imageWidth += NoV*2*DISTANCE_BETWEEN_COLUMN;
		imageWidth += 300;
		
		BufferedImage ret = new BufferedImage((int) imageWidth, (int) imageHeight, BufferedImage.TYPE_3BYTE_BGR);
		
		Graphics2D g2d = ret.createGraphics();
		Rectangle2D.Double r = new Rectangle2D.Double(0,0,imageWidth, imageHeight);
		g2d.setColor(new Color(245, 245, 245));
		g2d.fill(r);
		
		g2d.setColor(Color.BLACK);
		g2d.setFont(new Font("TimesRoman", Font.BOLD, 20));
		
		for(int i = 0; i < 2*NoV; i++) {
			g2d.draw(new Line2D.Double(DISTANCE_BETWEEN_COLUMN*(i+1), 50, DISTANCE_BETWEEN_COLUMN*(i+1), imageHeight-50));
			if(i%2==1) {
				g2d.drawString(String.valueOf((char)('A'+i/2))+"\u0305",(int) DISTANCE_BETWEEN_COLUMN*(i+1)-6, 45);
			}else {
				g2d.drawString(String.valueOf((char)('A'+i/2)),(int) DISTANCE_BETWEEN_COLUMN*(i+1)-6, 45);
			}
		}
		if(answer.length > 1) {
			if(output_type.equals("SOP")) {
				g2d.fill(new OR_Shape(imageWidth-125, (imageHeight-DISTANCE_BETWEEN_ROW*(answer.length))/2, answer.length));
			}else if(output_type.equals("POS")){
				g2d.fill(new AND_Shape(imageWidth-125, (imageHeight-DISTANCE_BETWEEN_ROW*(answer.length))/2, answer.length));
			}
		}else {
			g2d.draw(new Line2D.Double(imageWidth-125, imageHeight/2, imageWidth-75, imageHeight/2));
		}
		g2d.draw(new Line2D.Double(imageWidth-75, imageHeight/2, imageWidth-25, imageHeight/2));
		g2d.fillPolygon(new int[]{(int) (imageWidth-25), (int) (imageWidth-25-ARROWHEAD_SIZEX), (int) (imageWidth-25-ARROWHEAD_SIZEX)},new int[]{(int) (imageHeight/2), (int) (imageHeight/2+ARROWHEAD_SIZEY), (int) (imageHeight/2-ARROWHEAD_SIZEY)},3);
		
		double currentImageHeight = 75;
		int group = 0;
		for(int[] i:answer) {	
			if(i.length > 1) {
				if(output_type.equals("SOP")) {
					g2d.fill(new AND_Shape(imageWidth-250, currentImageHeight-DISTANCE_BETWEEN_ROW/2, i.length));
				}else if(output_type.equals("POS")) {
					g2d.fill(new OR_Shape(imageWidth-250, currentImageHeight-DISTANCE_BETWEEN_ROW/2, i.length));
				}
			}else {
				g2d.draw(new Line2D.Double(imageWidth-250, currentImageHeight, imageWidth-200, currentImageHeight));
			}
			double temp = Math.abs((2*group+1-answer.length)/2)*DISTANCE_BETWEEN_COLUMN/2;
			g2d.draw(new Line2D.Double(imageWidth-200, currentImageHeight+(i.length-1)*DISTANCE_BETWEEN_ROW/2, imageWidth-160+temp, currentImageHeight+(i.length-1)*DISTANCE_BETWEEN_ROW/2));
			g2d.draw(new Line2D.Double(imageWidth-160+temp, currentImageHeight+(i.length-1)*DISTANCE_BETWEEN_ROW/2, imageWidth-160+temp, (imageHeight-DISTANCE_BETWEEN_ROW*(answer.length-2*group-1))/2));
			g2d.draw(new Line2D.Double(imageWidth-160+temp, (imageHeight-DISTANCE_BETWEEN_ROW*(answer.length-2*group-1))/2, imageWidth-125, (imageHeight-DISTANCE_BETWEEN_ROW*(answer.length-2*group-1))/2));
			group++;
			for(int j:i) {
				int pos = j%NoV*2;
				if (j >= NoV) {
					pos++;
				}
				g2d.fill(new Ellipse2D.Double(DISTANCE_BETWEEN_COLUMN*(pos+1) - DOT_SIZE/2, currentImageHeight - DOT_SIZE/2, DOT_SIZE, DOT_SIZE));
				g2d.draw(new Line2D.Double(DISTANCE_BETWEEN_COLUMN*(pos+1), currentImageHeight, imageWidth-250, currentImageHeight));
				currentImageHeight += DISTANCE_BETWEEN_ROW;
			}
			currentImageHeight += DISTANCE_BETWEEN_ROW_GROUP-DISTANCE_BETWEEN_ROW;
			
		}
		
		return ret;
	}
}
