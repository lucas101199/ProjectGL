package GUI;

import CC.Direction;
import CC.FakeCommandControl;
import CC.Query;
import CC.State;
import Operative.ImplPourSimulation;

public class GUI_CC {

    static GUI gui;
    static FakeCommandControl cc;
    ImplPourSimulation simulation = new ImplPourSimulation(3, 3, 6);

    public GUI_CC() {
        gui = new GUI(this);
        cc = new FakeCommandControl(simulation);
    }

    public void emergency_Stop() {
        cc.EmergencyStop();
    }

    public void sendQuery(int floor, String direction) {
        cc.addQuery(new Query(floor, direction));
    }

    public void send_Query(int floor) {
        String direction = cc.directionElevator(floor);
        cc.addQuery(new Query(floor, direction));
    }

    public static void displayFloor() {
        int floor = cc.getFloor();
        gui.displayFloor(floor);
    }
}
