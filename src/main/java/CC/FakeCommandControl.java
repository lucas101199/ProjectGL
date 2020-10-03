package CC;
import Operative.ImplPourSimulation;


public class FakeCommandControl implements  CommandControl{

    private final ImplPourSimulation elevator;
    private int numFloor;
    private Direction direction;

    public FakeCommandControl(ImplPourSimulation elevator){
        this.elevator = elevator;
        this.elevator.setCommandControl(this);
        numFloor = 0;
        direction = Direction.Stop;
    }

    @Override
    public void handleQuery(Query req) {

        String order = req.getInstruction("Order");
        switch (order) {
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
        }
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
