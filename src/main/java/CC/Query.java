package CC;


public class Query {

    public int floor;
    public boolean isEmergencyStop;

    public Query(int floor) {
        this.floor = floor;
    }

    public Query(boolean emergencyStop){
        isEmergencyStop = emergencyStop;
    }

}
