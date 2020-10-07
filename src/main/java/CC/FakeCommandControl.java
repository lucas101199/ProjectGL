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
        numFloor = initFloor;
        direction = Direction.None;
        this.queriesReceived = new PriorityQueue<>();
    }

    @Override
    public void handleEvent(Event event) {
        /*
        while (true) {
            if (direction == Direction.Stop) {
                System.out.println(numFloor);
                if (!queriesReceived.isEmpty()) {
                    if (queriesReceived.peek().floor > numFloor) {
                        elevator.Up();
                        direction = Direction.Up;
                    } else if (queriesReceived.peek().floor < numFloor) {
                        elevator.Down();
                        direction = Direction.Down;
                    }
                } else
                    Stop();
            }
        }
        //Stop();*/
    }

    @Override
    public void updateFloor() {
        if(direction == Direction.Up) {
            numFloor++;
            // GUI_CC.displayFloor();
        }
        else if(direction == Direction.Down) {
            numFloor--;
            // GUI_CC.displayFloor();
        }
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
