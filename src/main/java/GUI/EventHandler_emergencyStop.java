package GUI;

import java.awt.event.ActionEvent;

public class EventHandler_emergencyStop implements EventHandler{

    public GUI_CC gui_cc;

    public EventHandler_emergencyStop(GUI_CC gui_cc) {
        this.gui_cc = gui_cc;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gui_cc.emergency_Stop();
    }
}
