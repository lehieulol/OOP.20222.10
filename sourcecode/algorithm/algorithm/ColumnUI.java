package algorithm.algorithm;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JList;
public class ColumnUI {
    private JFrame frame;
    private DefaultListModel<String> listModel;

    public ColumnUI(String title) {
        frame = new JFrame();
        frame.setTitle(title);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        listModel = new DefaultListModel<>();
        JList<String> resultList = new JList<>(listModel);
        panel.add(new JScrollPane(resultList), BorderLayout.CENTER);

        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    public void addResult(String result) {
        listModel.addElement(result);
    }

    public void display() {
        frame.setVisible(true);
    }

    public void close() {
        frame.dispose();
    }
}
