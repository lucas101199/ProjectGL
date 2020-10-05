package GUI;

import CC.FakeCommandControl;
import CC.Query;
import CC.State;
import Operative.ImplPourSimulation;

public class GUI_CC {

    GUI gui;
    FakeCommandControl cc;
    ImplPourSimulation simulation = new ImplPourSimulation();

    public GUI_CC() {
        this.gui = new GUI(this);
        this.cc = new FakeCommandControl(simulation);
    }

    public void emergency_Stop() {
        cc.Stop();
    }

    public void sendQuery(int floor, String direction) {
        cc.addQuery(new Query(floor, direction));
        displayFloor();
    }

    public void send_Query(int floor) {
        String direction = cc.directionElevator(floor);
        cc.addQuery(new Query(floor, direction));
        displayFloor();
    }

    public void displayFloor() {
        while (cc.getState() != State.Stopped) {
            int floor = cc.getFloor();
            gui.displayFloor(floor);
        }
    }
}
