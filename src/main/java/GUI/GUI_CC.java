package GUI;

import CC.FakeCommandControl;
import Operative.ImplPourSimulation;

public class GUI_CC {

    GUI gui;
    FakeCommandControl cc;
    ImplPourSimulation simulation = new ImplPourSimulation();

    public GUI_CC() {
        this.gui = new GUI(this);
        this.cc = new FakeCommandControl(simulation);
    }


}
