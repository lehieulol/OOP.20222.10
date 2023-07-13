package algorithm;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.*;
import java.util.List;

public class FirstColumn extends JFrame {
    private JList<String> groupList;

    public FirstColumn(Map<Integer, List<String>> groupsData) {
        setTitle("Minterm Groups");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create a DefaultListModel and populate it with the minterms and separators
        DefaultListModel<String> listModel = new DefaultListModel<>();

        for (int i = 0; i <= groupsData.size(); i++) {
            List<String> group = groupsData.getOrDefault(i, new ArrayList<>());
            group.sort(Comparator.comparingInt(Integer::parseInt));

            for (String minterm : group) {
                int value = Integer.parseInt(minterm, 2);
                listModel.addElement(value + " " + minterm);
            }

            if (!group.isEmpty() && i != groupsData.size()) {
                listModel.addElement(""); // Empty item for line separator
            }
        }

        // Create a JList to display the minterms and separators
        groupList = new JList<>(listModel);
        groupList.setCellRenderer(new SeparatorListCellRenderer());
        JScrollPane scrollPane = new JScrollPane(groupList);

        add(scrollPane, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    private static class SeparatorListCellRenderer extends DefaultListCellRenderer {
        private final Color separatorColor = Color.GRAY;
        private final Border separatorBorder = BorderFactory.createMatteBorder(1, 0, 0, 0, separatorColor);

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (value.toString().isEmpty()) {
                label.setBorder(separatorBorder);
                label.setBackground(list.getBackground());
            }

            return label;
        }
    }

    public static void main(String[] args) {
        List<MinTerm> minTerm = new ArrayList<>();
        MinTerm a = new MinTerm("7",3);
        MinTerm b = new MinTerm("1",3);
        MinTerm c = new MinTerm("3",3);
        MinTerm d = new MinTerm("4",3);
        // Example usage

        minTerm.add(a);
        minTerm.add(b);
        minTerm.add(c);
        minTerm.add(d);
        Map<Integer, List<String>> groupsData = MintermGrouper.groupMintermsByOccurrences(minTerm);

        SwingUtilities.invokeLater(() -> new FirstColumn(groupsData));
    }
}