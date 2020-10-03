package CC;


public class Query implements Comparable<Query> {

    public String floor;
    public String direction;


    public Query(String floor, String direction) {
        this.floor = floor;
        this.direction = direction;
    }

    public String getFloor() {
        return floor;
    }

    public String getDirection() {
        return direction;
    }

    @Override
    public int compareTo(Query o) {
        return 0;
    }
}
