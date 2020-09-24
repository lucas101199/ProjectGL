package GUI;

import java.awt.event.ActionEvent;

public class EventHandler_queries implements EventHandler {

    public int floor, direction;

    public EventHandler_queries(int floor, int direction) {
        this.floor = floor;
        this.direction = direction;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //System.out.println(floor);
        //System.out.println(direction);
    }
}
