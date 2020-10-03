package CC;
import Operative.ImplPourSimulation;

import java.util.HashMap;
import java.util.PriorityQueue;


public class FakeCommandControl implements  CommandControl{

    private final ImplPourSimulation elevator;
    private int numFloor;
    private Direction direction;
    public PriorityQueue<Query> Instructions;

    public FakeCommandControl(ImplPourSimulation elevator){
        this.elevator = elevator;
        this.elevator.setCommandControl(this);
        numFloor = 0;
        direction = Direction.Stop;
        this.Instructions = new PriorityQueue<>();
    }

    public void addQuery(Query cc) {
        Instructions.add(cc);
    }

    public void Stop() {
        direction = Direction.Stop;
    }

    @Override
    public void handleQuery(Query req) {

        /*switch (order) {
            case "Up" -> {
                elevator.Up();
                direction = Direction.Up;
            }
            case "Down" -> {
                elevator.Down();
                direction = Direction.Down;
            }
            case "NextFloor" -> elevator.stopNextFloor();
            default -> elevator.emergencyStop();
        }*/
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
}