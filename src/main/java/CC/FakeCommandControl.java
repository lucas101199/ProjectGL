package CC;
import Operative.ImplPourSimulation;

import java.util.HashMap;
import java.util.PriorityQueue;


public class FakeCommandControl implements  CommandControl{

    private final ImplPourSimulation elevator;
    private int numFloor;
    private Direction direction;
    public PriorityQueue<Query> queriesReceived;

    /**
     *
     * @param elevator
     * @param initFloor
     */
    public FakeCommandControl(ImplPourSimulation elevator, int initFloor){
        this.elevator = elevator;
        this.elevator.setCommandControl(this);
        numFloor = initFloor;
        direction = Direction.None;
        this.queriesReceived = new PriorityQueue<>();
    }

    /**
     *
     * @param event
     */
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

    /**
     *
     * @return int number of the floor
     */
    @Override
    public int getFloor() {
        return numFloor;
    }

    /**
     *
     * @return CCState
     */
    @Override
    public CCState getState() {
        return null;
    }

    /**
     *
     * @return Direction
     */
    @Override
    public Direction getDirection() {
        return direction;
    }

    /**
     *
     * @param direc
     */
    public void setDirection(Direction direc){
        direction = direc;
    }

}
