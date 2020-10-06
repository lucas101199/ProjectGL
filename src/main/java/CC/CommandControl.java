package CC;

import java.util.PriorityQueue;

public interface CommandControl {
    void handleQuery();
    void updateFloor();
    int getFloor();
    State getState();
}
