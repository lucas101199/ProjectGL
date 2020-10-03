package Operative;

import CC.CommandControl;
import java.util.Timer;


public class ImplPourSimulation implements InterfaceMaterielle{
    private Timer temporisateur;
    private StateEngine state;
    private CommandControl commandControl;


    public ImplPourSimulation(){
        state = StateEngine.Stopped;
    }

    public ImplPourSimulation(double tpsDistEtage , double tpsRalentissement){

    }

    @Override
    public void Up() {
        if(state == StateEngine.goingDown)
            emergencyStop();
        else
            state = StateEngine.goingUp;
    }

    @Override
    public void Down() {
        if(state == StateEngine.goingUp)
            emergencyStop();
        else
            state = StateEngine.goingDown;
    }

    @Override
    public void emergencyStop() {
        state = StateEngine.Stopped;
    }

    @Override
    public void stopNextFloor() {
        commandControl.updateFloor();
        state = StateEngine.Stopped;
    }

    @Override
    public void sendReadyToGo() {

    }

    @Override
    public void sendFloorPassed() {

    }

    //Pour tester le bon fonctionnement du matériel
    public StateEngine getStateEngine(){
        return state;
    }

    public void setCommandControl(CommandControl cc){
        commandControl = cc;
    }


}
