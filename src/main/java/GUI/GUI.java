package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

public class GUI extends JFrame {

    public JPanel button_group,simulation_part, statePanel;
    public JLabel screen;
    public GridBagConstraints gbc_button;
    public ArrayList<JButton> button_floor;
    public JButton emergency;
    private JComboBox<String> floorQueries, directionRequested;
    public GUI_CC gui_cc;

    /**
     *
     * @param gui_cc
     */
    public GUI(GUI_CC gui_cc) {

        this.gui_cc = gui_cc;
        button_floor = new ArrayList<>();
        Border blackLine = BorderFactory.createLineBorder(Color.BLACK);

        setTitle("Elevator");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 350);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        statePanel = new JPanel();
        statePanel.setBorder(BorderFactory.createTitledBorder("Elevator State"));
        statePanel.setLayout(new BoxLayout(statePanel, BoxLayout.X_AXIS));
        JLabel state = new JLabel("WAITING");
        state.setBorder(blackLine);
        state.setHorizontalAlignment(JLabel.CENTER);
        state.setFont(new Font(Font.DIALOG, Font.BOLD, 40));
        statePanel.add(state);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weighty = 0.5;
        getContentPane().add(statePanel, constraints);

        button_group = new JPanel();
        button_group.setBorder(BorderFactory.createTitledBorder("Elevator View"));
        button_group.setLayout(new BoxLayout(button_group, BoxLayout.Y_AXIS));
        //emergency stop button

        //Screen with the floor
        screen = new JLabel();
        screen.setBorder(blackLine);
        screen.setText("0");
        screen.setHorizontalAlignment(JLabel.CENTER);
        screen.setFont(new Font(Font.DIALOG, Font.BOLD, 40));
        screen.setMaximumSize(new Dimension(150, screen.getMinimumSize().height));
        button_group.add(screen);

        emergency = new JButton("Emergency Stop");
        emergency.setMaximumSize(new Dimension(150, emergency.getMinimumSize().height));
        emergency.addActionListener(new  EventHandler_emergencyStop(gui_cc));
        button_group.add(emergency);

        gbc_button = new GridBagConstraints();
        gbc_button.fill = GridBagConstraints.BOTH;
        gbc_button.gridx = 0;
        gbc_button.gridy = 1;
        gbc_button.weightx = 0.5;
        gbc_button.insets = new Insets(0, 0,0,50);
        add(button_group, gbc_button);

        simulation_part = new JPanel();
        floorQueries = new JComboBox<>();
        floorQueries.setMaximumSize(new Dimension(300, 50));
        directionRequested = new JComboBox<>();
        directionRequested.setMaximumSize(new Dimension(300, 50));
        directionRequested.addItem("Up ʌ");
        directionRequested.addItem("Down v");

        JButton validateButton = new JButton("Send Query");
        simulation_part.setBorder(BorderFactory.createTitledBorder("Floor Queries"));
        simulation_part.setLayout(new GridLayout(3,1));
        simulation_part.add(floorQueries);
        simulation_part.add(directionRequested);
        validateButton.setMaximumSize(new Dimension(300, 50));
        validateButton.addActionListener(new EventHandler_queries(floorQueries, directionRequested, gui_cc));
        simulation_part.add(validateButton);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 1;
        add(simulation_part, c);
        setVisible(true);
    }

    /**
     * Add a button to the GUI
     * @param name_button
     */
    public void add_Button(String name_button) {
        JButton button = new JButton(name_button);
        button.setMaximumSize(new Dimension(150,button.getMinimumSize().height));
        button.addActionListener(new EventHandler_floor(name_button, gui_cc));
        button_floor.add(button);
        button_group.add(button);
        floorQueries.addItem("Call from floor " + name_button);
        button_group.updateUI();
    }

    /**
     * Set the text in the screen with the floor
     * @param numFloor
     */
    public void displayFloor(int numFloor) {
        screen.setText(String.valueOf(numFloor));
        screen.updateUI();
    }

    /**
     *
     * @param floor
     * @return the button corresponding to the floor
     */
    public JButton getButton(int floor) {
        return button_floor.get(floor);
    }

    /**
     * Turn the button that has been pressed by the user by changing the border in yellow
     * @param floor
     */
    public void turnOffButton(String floor) {
        JButton buttonToLight = getButton(Integer.parseInt(floor));
        buttonToLight.setBorderPainted(true);
    }

    /**
     * @param floor which is passed by the elevator
     * @return true if the button has been pressed
     */
    public boolean IsButtonTurnOn(int floor) {
        Border yellowLine = BorderFactory.createLineBorder(Color.YELLOW);
        JButton button = getButton(floor);
        return button.getBorder().equals(yellowLine);
    }
 }
