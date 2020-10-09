package GUI;

import CC.*;
import CC.Event;
import Operative.ImplPourSimulation;
import Operative.InterfaceMaterielle;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GUI_CC {

    static GUI gui;
    static CommandControlImpl cci;
    InterfaceMaterielle simulation = new ImplPourSimulation(0.7, 0.5, 5,5,0);

    public GUI_CC() {
        gui = new GUI(this);
        cci = new CommandControlImpl(simulation, 0);
    }

    /**
     * Raise an event when the user press the button emergency stop
     */
    public void emergency_Stop() {
        cci.handleEvent(Event.EMMERGENCY_STOP);
    }

    /**
     *
     * @param floor
     * @param direction
     * Add a query to the queue when the user is outside of the elevator
     */
    public void sendQuery(int floor, String direction) {
        Direction _direction;
        if (direction.equals("Up")) _direction = Direction.Up;
        else _direction = Direction.Down;
        cci.handleEvent(Event.USER_REQUEST.linkQuery(new Query(floor, _direction)));
    }

    /**
     *
     * @param floor
     * Add a query to the queue when the user is inside of the elevator
     */
    public void send_Query(int floor) {
        cci.handleEvent(Event.USER_REQUEST.linkQuery(new Query(floor)));
    }

    /**
     * Display the floor on the screen in the GUI
     */
    public static void displayFloor() {
        int floor = cci.getFloor();
        gui.displayFloor(floor);
        if (gui.IsButtonTurnOn(floor) && cci.getState() == CCState.IS_STOPPED)
            gui.turnOffButton(String.valueOf(floor));
    }

    /**
     * Change the border in yellow when a button is pressed by the user
     * @param floor
     */
    public void light_Button(String floor) {
        Border yellowLine = BorderFactory.createLineBorder(Color.YELLOW);
        JButton buttonToLight = gui.getButton(Integer.parseInt(floor));
        buttonToLight.setBorder(yellowLine);
    }
}
