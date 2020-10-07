package GUI;

import CC.FakeCommandControl;
import CC.Query;
import Operative.ImplPourSimulation;

public class GUI_CC {

    GUI gui;
    FakeCommandControl cc;
    ImplPourSimulation simulation = new ImplPourSimulation();

    public GUI_CC() {
        this.gui = new GUI(this);
    }

    public void emergency_Stop() {
        cc.handleQuery(new Query(true));
    }
}
