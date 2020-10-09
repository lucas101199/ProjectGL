package CC;


public class Query implements Comparable<Query> {

    private int _floor;
    private Direction _direction;
    private boolean _isEmergencyStop;

    /**
     * Create a new query with only a floor number
     * @param floor
     */
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
     * @return the floor of the query
     */
    public int getFloor() {
        return _floor;
    }

    /**
     *
     * @return the direction of the query
     */
    public Direction getDirection() {
        return _direction;
    }

    /**
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(Query o) {
        return 0;
    }
}
