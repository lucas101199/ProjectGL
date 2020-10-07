package CC;
import Operative.ImplPourSimulation;

import java.util.HashMap;
import java.util.PriorityQueue;


public class FakeCommandControl implements  CommandControl{

    private final ImplPourSimulation elevator;
    private int numFloor;
    private Direction direction;
    public PriorityQueue<Query> queriesReceived;

    public FakeCommandControl(ImplPourSimulation elevator, int initFloor){
        this.elevator = elevator;
        this.elevator.setCommandControl(this);
        numFloor = 0;
        direction = Direction.Stop;
        this.queriesReceived = new PriorityQueue<>();
        numFloor = initFloor;
    }

    @Override
    public void handleQuery(Query query) {
        if(!query.isEmergencyStop){
            if(query.floor > numFloor){
                elevator.Up();
                direction = Direction.Up;
            }
            else if(query.floor < numFloor){
                elevator.Down();
                direction = Direction.Down;
            }
        }
        else
            direction = Direction.Stop;
    }

    @Override
    public void updateFloor() {
        if(direction == Direction.Up)
            numFloor++;
        else if(direction == Direction.Down)
            numFloor--;
    }

    @Override
    public int getFloor() {
        return numFloor;
    }

    @Override
    public State getState() {
        return null;
    }

    public void setDirection(Direction direc){
        direction = direc;
    }

}
