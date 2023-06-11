package UI;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		int w = 1000, h = 800;
		JFrame main = new GUI_Frame("Logic expression normalizer");
		main.setSize(w,h);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setVisible(true);
	}

}
