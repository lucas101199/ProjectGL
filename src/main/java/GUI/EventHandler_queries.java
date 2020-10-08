package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class EventHandler_queries implements EventHandler {

    public JComboBox<String> floor, direction;
    public GUI_CC gui_cc;

    public EventHandler_queries(JComboBox<String> floor, JComboBox<String> direction, GUI_CC gui_cc) {
        this.floor = floor;
        this.direction = direction;
        this.gui_cc = gui_cc;
    }

    /**
     *
     * @param e
     * Is trigger when a new query need to be created
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        gui_cc.sendQuery(floor.getSelectedIndex(), String.valueOf(direction.getSelectedIndex()));
    }
}
