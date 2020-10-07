package GUI;

import org.junit.runner.Request;

enum Event{
    CAPEUR_UP;
     public Request q;
}


public class Gui_elevator {

    public static void main(String[] args) {
        try {
            GUI_CC elevator = new GUI_CC();
            GUI_CC.gui.add_Button("0");
            GUI_CC.gui.add_Button("1");
            GUI_CC.gui.add_Button("2");
            GUI_CC.gui.add_Button(  "3");
            System.out.println((int)(1.6));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
