package GUI;

import javax.swing.*;
import java.awt.*;

public class GUI extends Frame {

    public JFrame frame;
    public JPanel button_group;
    public GridBagConstraints gbc_button;

    public GUI() {

        frame = new JFrame("Elevator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 1200);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        gbc_button = new GridBagConstraints();
        button_group = new JPanel();
        button_group.setLayout(new GridBagLayout());
        button_group.setSize(300, 1200);

        frame.getContentPane().add(button_group);
        frame.pack();
        frame.setVisible(true);
    }
}
