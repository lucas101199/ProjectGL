package GUI;

import java.awt.event.ActionEvent;

public class EventHandler_floor implements EventHandler {

    public String floor;
    public GUI_CC gui_cc;

    public EventHandler_floor(String floor, GUI_CC gui_cc) {
        this.floor = floor;
        this.gui_cc = gui_cc;
    }

    /**
     * Is trigger when a new query need to be created from the interior of the elevator
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        gui_cc.send_Query(Integer.parseInt(floor));
        gui_cc.light_Button(floor);
    }
}
