package GUI;

import org.junit.runner.Request;

import java.util.PriorityQueue;

enum Event{
    CAPEUR_UP;
     public Request q;
}


public class Gui_elevator {
    public static boolean ok;
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
        ok = true;
        var c = new PriorityQueue<Integer>((a,b)->{ if(ok){
                                                    if( a > b) return 1;
                                                    else if(a < b) return -1;
                                                    else return 0;}
                                                    else{
                                                    if(a > b) return -1;
                                                    else if(a < b) return 1;
                                                    else return 0;}
                                                    });
        c.add(5);
        c.add(1);
        //ok = false;
        c.add(c.poll());
        System.out.println(c.poll() + "," + c.poll());
    }
}
