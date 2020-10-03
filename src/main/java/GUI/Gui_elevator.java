package GUI;

public class Gui_elevator {

    public static void main(String[] args) {
        try {
            GUI_CC elevator = new GUI_CC();
            elevator.gui.add_Button("0");
            elevator.gui.add_Button("1");
            elevator.gui.add_Button("2");
            elevator.gui.add_Button("3");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
