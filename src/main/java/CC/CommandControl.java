package CC;

public interface CommandControl {
    void handleQuery(Query req);
    void updateFloor();
    int getFloor();
    State getState();
}
