package GUI;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class GUI extends Frame {

    public JFrame frame;
    public JPanel button_group;
    public GridBagConstraints gbc_button;
    public ArrayList<JButton> button_floor;

    public GUI() {
        button_floor = new ArrayList<>();

        frame = new JFrame("Elevator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1200);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        gbc_button = new GridBagConstraints();
        button_group = new JPanel();
        button_group.setLayout(new GridBagLayout());
        button_group.setSize(300, 1200);

        //Buttons for the elevator

        frame.getContentPane().add(button_group);
        frame.pack();
        frame.setVisible(true);
    }

    public void add_Button(String name_button) {
        JButton name = new JButton(name_button);
        button_floor.add(name);
        button_group.add(name);
        button_group.updateUI();
    }
}
