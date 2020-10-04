package Operative;

import CC.CommandControl;
import CC.Direction;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class ImplPourSimulation implements InterfaceMaterielle{
    private Timer coolDown;
    private StateEngine state;
    private CommandControl commandControl;
    private double _vSpeed;
    private double _approachSpeed;
    private double _distanceBtwFloor;
    private double distanceEllapsed;
    private long resfreshDelay;
    private int _currentFloor;

    private class GoingUpDownAction extends TimerTask{
        @Override
        public void run() {
            distanceEllapsed += _vSpeed * resfreshDelay;
            System.out.println(distanceEllapsed);
            if((int)(distanceEllapsed / _distanceBtwFloor) != _currentFloor) {
                commandControl.updateFloor();
                if(state == StateEngine.goingUp)
                    _currentFloor++;
                else if(state == StateEngine.goingDown)
                    _currentFloor--;
            }
        }
    }


    public ImplPourSimulation(){
        state = StateEngine.Stopped;
    }

    /**
    @param : vSpeed, approachSpeed are in meters per second and must be positive.
     **/
    public ImplPourSimulation(double vSpeed , double approachSpeed, double distanceBetweenFloor){
        assert vSpeed > 0  && approachSpeed > 0 && distanceBetweenFloor > 0: "Error parameter muste be > 0";
        state = StateEngine.Stopped;
        _vSpeed = vSpeed / 1000;
        _approachSpeed = approachSpeed / 1000;
        _distanceBtwFloor = distanceBetweenFloor;
        distanceEllapsed = .0;
        resfreshDelay = 100;    //Can be calculated from v_speed
        coolDown = new Timer();
        _currentFloor = 0;
    }

    @Override
    public void Up() {
        if(state == StateEngine.goingDown)
            emergencyStop();
        else{
            state = StateEngine.goingUp;
            coolDown.schedule(new GoingUpDownAction(),0, resfreshDelay);
        }
    }

    @Override
    public void Down() {
        if(state == StateEngine.goingUp)
            emergencyStop();
        else {
            state = StateEngine.goingDown;
            coolDown.schedule(new GoingUpDownAction(),0, resfreshDelay);
        }
    }

    @Override
    public void emergencyStop() {
        coolDown.cancel();
        coolDown.purge();
        state = StateEngine.Stopped;
    }

    @Override
    public void stopNextFloor() {
        coolDown.cancel();
        coolDown.purge();

        new Thread(()->{
            if(state == StateEngine.goingUp)
                _currentFloor++;
            else if(state == StateEngine.goingDown)
                _currentFloor--;
            var distanceRemaining =Math.abs(_currentFloor * _distanceBtwFloor - distanceEllapsed);
            var time = distanceRemaining / _approachSpeed;
            System.out.println("Temps restant :" + time);
            try{
                TimeUnit.MILLISECONDS.sleep((long)time);
            }
            catch (Exception e){e.printStackTrace();}
            distanceEllapsed += distanceRemaining;
            commandControl.updateFloor();
            state = StateEngine.Stopped; }).start();
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
