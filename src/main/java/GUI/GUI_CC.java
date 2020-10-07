package GUI;

import CC.Direction;
import CC.FakeCommandControl;
import CC.Query;
import Operative.ImplPourSimulation;

public class GUI_CC {

    static GUI gui;
    static FakeCommandControl cc;
    ImplPourSimulation simulation = new ImplPourSimulation(0.7, 0.5, 5,5,0);

    public GUI_CC() {
        gui = new GUI(this);
        cc = new FakeCommandControl(simulation, 0);
    }

    public void emergency_Stop() {
        cc.setDirection(Direction.Stop);
    }

    public void sendQuery(int floor, String direction) {
        cc.queriesReceived.add(new Query(floor, direction));
    }

    public void send_Query(int floor) {
        String direction = cc.directionElevator(floor);
        cc.queriesReceived.add(new Query(floor, direction));
    }

    public static void displayFloor() {
        int floor = cc.getFloor();
        gui.displayFloor(floor);
    }
}
