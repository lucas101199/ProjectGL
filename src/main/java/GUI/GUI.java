package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.util.ArrayList;

public class GUI extends Frame {

    public JFrame frame;
    public JPanel button_group, screen;
    public GridBagConstraints gbc_button;
    public ArrayList<JButton> button_floor;
    public JButton emergency;

    public GUI() {
        button_floor = new ArrayList<>();
        Border blackLine = BorderFactory.createLineBorder(Color.BLACK);

        frame = new JFrame("Elevator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1200);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridBagLayout());

        gbc_button = new GridBagConstraints();
        button_group = new JPanel();
        button_group.setLayout(new GridBagLayout());
        //button_group.setBorder(blackLine);
        button_group.setSize(300, 800);
        gbc_button.gridwidth = GridBagConstraints.REMAINDER;

        //emergency stop button
        emergency = new JButton("stop");
        button_group.add(emergency, gbc_button);

        //Screen with the floor
        screen = new JPanel();
        screen.setBorder(blackLine);
        screen.setSize(300, 200);

        //add all the components to the frame
        frame.getContentPane().add(screen, gbc_button);
        frame.getContentPane().add(button_group, gbc_button);
        frame.setVisible(true);
    }

    public void add_Button(String name_button) {
        JButton name = new JButton(name_button);
        button_floor.add(name);
        button_group.add(name, gbc_button);
        button_group.updateUI();
    }
}
