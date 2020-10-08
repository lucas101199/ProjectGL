package CC;

import java.util.ArrayList;
import java.util.PriorityQueue;

public interface CommandControl {
    void handleEvent(Event event);
    void updateFloor();
    int getFloor();
    CCState getState();
    Direction getDirection();

}
