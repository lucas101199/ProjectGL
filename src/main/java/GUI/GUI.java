package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.util.ArrayList;

public class GUI extends JFrame {

    public JPanel button_group,simulation_part;
    public JLabel screen;
    public GridBagConstraints gbc_button;
    public ArrayList<JButton> button_floor;
    public JButton emergency;

    public GUI() {
        button_floor = new ArrayList<>();
        Border blackLine = BorderFactory.createLineBorder(Color.BLACK);

        setTitle("Elevator");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        button_group = new JPanel();
        button_group.setLayout(new BoxLayout(button_group, BoxLayout.Y_AXIS));
        //emergency stop button

        //Screen with the floor
        screen = new JLabel();
        screen.setBorder(blackLine);
        screen.setText("RDC");
        screen.setHorizontalAlignment(JLabel.CENTER);
        screen.setFont(new Font(Font.DIALOG, Font.BOLD, 40));
        screen.setMaximumSize(new Dimension(150, screen.getMinimumSize().height));
        button_group.add(screen);

        emergency = new JButton("Emergency Stop");
        emergency.setMaximumSize(new Dimension(150,emergency.getMinimumSize().height));
        button_group.add(emergency);

        gbc_button = new GridBagConstraints();
        gbc_button.fill = GridBagConstraints.BOTH;
        gbc_button.gridx = 0;
        gbc_button.gridy = 0;
        gbc_button.weightx = 0.5;
        gbc_button.insets = new Insets(0, 50,0,50);
        gbc_button.gridwidth = 2;
        gbc_button.ipadx = 500;
        add(button_group, gbc_button);

        simulation_part = new JPanel();
        simulation_part.setLayout(new BoxLayout(simulation_part, BoxLayout.PAGE_AXIS));

        var c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 2;
        c.gridy = 0;
        add(simulation_part, c);
        setVisible(true);
    }

    public void add_Button(String name_button) {
        JButton button = new JButton(name_button);
        button.setMaximumSize(new Dimension(150,button.getMinimumSize().height));
        button_floor.add(button);
        button_group.add(button);
        button = new JButton("Call From Floor "+ name_button);
        simulation_part.add(button);
        button_group.updateUI();
    }
}
