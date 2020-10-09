package GUI;

import CC.CommandControlImpl;
import CC.Direction;
import CC.Event;
import CC.FakeCommandControl;
import CC.Query;
import Operative.ImplPourSimulation;
import Operative.InterfaceMaterielle;

public class GUI_CC {

    static GUI gui;
    static CommandControlImpl cci;
    InterfaceMaterielle simulation = new ImplPourSimulation(0.7, 0.5, 5,5,0);

    public GUI_CC() {
        gui = new GUI(this);
        cci = new CommandControlImpl(simulation, 0);
    }

    public void emergency_Stop() {
        //cci.
    }

    /**
     *
     * @param floor
     * @param direction
     * Add a query to the queue when the user is outside of the elevator
     */
    public void sendQuery(int floor, String direction) {
       /* Direction _direction = cci.DirElevator(direction);
        cci.handleEvent(Event.USER_REQUEST.linkQuery(new Query(floor, _direction)));*/
    }

    /**
     *
     * @param floor
     * Add a query to the queue when the user is inside of the elevator
     */
    public void send_Query(int floor) {
       /* Direction direction = cci.DirectionElevator(floor);
        cci.handleEvent(Event.USER_REQUEST.linkQuery(new Query(floor, direction)));*/
    }

    /**
     * Display the floor on the screen in the GUI
     */
    public static void displayFloor() {
        int floor = cci.getFloor();
        gui.displayFloor(floor);
    }
}
