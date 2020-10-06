package CC;

import GUI.GUI_CC;
import Operative.ImplPourSimulation;
import org.w3c.dom.events.EventListener;

import java.util.Objects;
import java.util.PriorityQueue;


public class FakeCommandControl implements  CommandControl{

    private final ImplPourSimulation elevator;
    private int numFloor;
    private Direction direction;
    public PriorityQueue<Query> queriesReceived;
    final Thread t;

    public FakeCommandControl(ImplPourSimulation elevator){
        this.elevator = elevator;
        this.elevator.setCommandControl(this);
        numFloor = 0;
        direction = Direction.Stop;
        this.queriesReceived = new PriorityQueue<>();
        t = new Thread(this::handleQuery);
        t.start();
    }

    public void addQuery(Query cc) {
        queriesReceived.add(cc);
    }

    public void Stop() {
        direction = Direction.Stop;
    }

    public void EmergencyStop() {
        Stop();
        elevator.emergencyStop();
    }

    public String directionElevator(int arrivalFloor) {
        if (arrivalFloor > numFloor) return "Up";
        return "Down";
    }

    @Override
    public void handleQuery() {
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
        //Stop();
    }

    @Override
    public void updateFloor() {
        if(direction == Direction.Up) {
            numFloor++;
            GUI_CC.displayFloor();
        }
        else if(direction == Direction.Down) {
            numFloor--;
            GUI_CC.displayFloor();
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
