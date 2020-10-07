package CC;

import java.util.PriorityQueue;

public interface CommandControl {
    void handleEvent(Event event);
    void updateFloor();
    int getFloor();
    State getState();
}
