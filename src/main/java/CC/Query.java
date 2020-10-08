package CC;


public class Query implements Comparable<Query> {

    private int _floor;
    private Direction _direction;
    private boolean _isEmergencyStop;

    public Query(int floor) {
        _floor = floor;
        _direction = Direction.None;
        _isEmergencyStop = false;
    }

    /**
     *
     * @param floor
     * @param direction
     * Create a new query
     */
    public Query(int floor, Direction direction){
        _floor = floor;
        _direction = direction;
        _isEmergencyStop = false;
    }

    /**
     *
     * @param isEmergencyStop
     * Create a new query for an emergency stop action
     */
    public Query(boolean isEmergencyStop){
        _isEmergencyStop = true;
    }

    /**
     *
     * @return
     */
    public int getFloor() {
        return _floor;
    }

    public Direction getDirection() {
        return _direction;
    }

    @Override
    public int compareTo(Query o) {
        return 0;
    }
}
