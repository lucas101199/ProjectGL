package GUI;

import java.awt.event.ActionEvent;

public class EventHandler_floor implements EventHandler {

    public String floor;

    public EventHandler_floor(String floor) {
        this.floor = floor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int num_floor = Integer.parseInt(floor);
        //System.out.println(num_floor);
    }
}
