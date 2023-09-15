import javax.swing.*;

public class MainClass {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createGUI());
        // String userLink = JOptionPane.showInputDialog(null, "Paste your website's link here");
    }

    private static void createGUI(){
        JFrame frame = new JFrame("Webpage Link");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit button
        frame.setSize(450, 200);

        JPanel panel = new JPanel();
        JLabel label = new JLabel("Input your website's link here");
        JTextField textField = new JTextField(20);
        JLabel label1 = new JLabel();

        panel.add(label);
        panel.add(textField);
        panel.add(label1);

        frame.add(panel);
        frame.setVisible(true);

    }
}
