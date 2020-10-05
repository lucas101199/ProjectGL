package CC;


public class Query implements Comparable<Query> {

    public int floor;
    public String direction;
    public boolean isEmergencyStop;

    public Query(int floor, String direction) {
        this.floor = floor;
        this.direction = direction;
    }

    public int getFloor() {
        return floor;
    }

    public String getDirection() {
        return direction;
    }

    public Query(boolean emergencyStop){
        isEmergencyStop = emergencyStop;
    }

    @Override
    public int compareTo(Query o) {
        return 0;
    }
}
