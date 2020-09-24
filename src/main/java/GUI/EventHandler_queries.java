package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class EventHandler_queries implements EventHandler {

    public JComboBox<String> floor, direction;

    public EventHandler_queries(JComboBox<String> floor, JComboBox<String> direction) {
        this.floor = floor;
        this.direction = direction;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(floor.getSelectedIndex());
        System.out.println(direction.getSelectedIndex());
    }
}
