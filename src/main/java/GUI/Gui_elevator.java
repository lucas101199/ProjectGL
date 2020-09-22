package GUI;

public class Gui_elevator {

    public static void main(String[] args) {
        try {
            GUI elevator = new GUI();
            elevator.add_Button("1");
            elevator.add_Button("2");
            elevator.add_Button("3");
            elevator.add_Button("4");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
