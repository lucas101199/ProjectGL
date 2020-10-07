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

    public Query(int floor, Direction direction){
        _floor = floor;
        _direction = direction;
        _isEmergencyStop = false;
    }

    public Query(boolean isEmergencyStop){
        _isEmergencyStop = true;
    }

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
