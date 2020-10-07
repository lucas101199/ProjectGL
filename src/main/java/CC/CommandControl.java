package CC;

import java.util.PriorityQueue;

public interface CommandControl {
    void handleQuery(Query query);
    void updateFloor();
    int getFloor();
    State getState();
}
