package ui.Output_Panel;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import javax.swing.*;

public class OutputPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public OutputPanel(int width, int height) {
		setSize(width, height);
		setLayout(new BorderLayout());
	}
	
	public void output_panel_generator(int[][] answer, Integer NoV, String output_type, JPanel process) {
		removeAll();
		JPanel _p = new JPanel();
		JPanel x = new JPanel();
		x.add(_p);
		JScrollPane sp = new JScrollPane(x, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(sp);
		_p.setLayout(new BoxLayout(_p, BoxLayout.Y_AXIS));
		_p.add(process);
		_p.add(new JLabel("Output:"));
		StringBuilder sb = new StringBuilder();
		int[][] zero_1 = {{}};
		boolean is_1 = Arrays.deepEquals(answer, zero_1);
		if(answer.length==0 || is_1) {
			int val = 0;
			if ((output_type.equals("SOP") && is_1)||(output_type.equals("POS") && !is_1)) {
				val = 1;
			}
			JTextField tmp = new JTextField("Separate: y = "+val);
			tmp.setFont(new Font("TimesRoman", Font.BOLD, 20));
			_p.add(tmp);
			revalidate();
			return;
		}
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
			sb.delete(sb.length() - 3, sb.length());
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
				sb.delete(sb.length() - 3, sb.length());
				sb.append(")");	
			}
		}
		JTextField tmp = new JTextField(sb.toString());
		tmp.setFont(new Font("TimesRoman", Font.BOLD, 20));
		_p.add(tmp);
		_p.add(new JLabel(new ImageIcon(draw_logic_circuit(answer, NoV, output_type))));
		
		revalidate();
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
