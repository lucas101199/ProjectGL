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
    private  double _vSpeed ;
    private  double _approachSpeed;
    private  double _distanceBtwFloor;
    private double distanceEllapsed;
    private  long resfreshDelay;
    private int _currentFloor;
    private  TimerTask _currentTask;
    private int _nbOfFloor;

    private class GoingUpDownAction extends TimerTask{
        @Override
        public void run() {
            if((distanceEllapsed < (1./3) * _distanceBtwFloor && state == StateEngine.goingDown )|| (distanceEllapsed > (_nbOfFloor-1)*_distanceBtwFloor
                                                                                   + (2./3)*_distanceBtwFloor && state == StateEngine.goingUp))
            {
                System.out.println("OUFFFF" + (_nbOfFloor-1)*_distanceBtwFloor + (2./3)*_distanceBtwFloor);
                _currentTask.cancel();
                stopNextFloor();
            }
            if(state == StateEngine.goingUp)
                distanceEllapsed += _vSpeed * resfreshDelay;
            else if(state == StateEngine.goingDown)
                distanceEllapsed -= _vSpeed * resfreshDelay;
            System.out.println(distanceEllapsed);
            if((int)(distanceEllapsed / _distanceBtwFloor) != _currentFloor)
                updateFloor();
        }
    }


    public ImplPourSimulation(){
        state = StateEngine.Stopped;
    }

    /**
    @param : vSpeed, approachSpeed are in meters per second and must be positive.
     **/
    public ImplPourSimulation(double vSpeed , double approachSpeed, double distanceBetweenFloor,
                              int nbOfFloor, int initialFloor){
        assert vSpeed > 0  && approachSpeed > 0 && distanceBetweenFloor > 0: "Error parameter muste be > 0";
        state = StateEngine.Stopped;
        _vSpeed = vSpeed / 1000;
        _approachSpeed = approachSpeed / 1000;
        _distanceBtwFloor = distanceBetweenFloor;
        _currentFloor = initialFloor;
        distanceEllapsed = initialFloor * distanceBetweenFloor;
        resfreshDelay = 100;    //Can be calculated from v_speed
        coolDown = new Timer();
        _nbOfFloor = nbOfFloor;

    }

    @Override
    public void Up() {
        if(distanceEllapsed < _nbOfFloor * _distanceBtwFloor) {
            if (state == StateEngine.goingDown)
                emergencyStop();
            else {
                state = StateEngine.goingUp;
                _currentTask = new GoingUpDownAction();
                coolDown.schedule(_currentTask, 0, resfreshDelay);
            }
        }
    }

    @Override
    public void Down() {
        if(distanceEllapsed > 0) {
            if (state == StateEngine.goingUp)
                emergencyStop();
            else {
                state = StateEngine.goingDown;
                _currentTask = new GoingUpDownAction();
                coolDown.schedule(_currentTask, 0, resfreshDelay);
            }
        }
    }

    @Override
    public void emergencyStop() {
        _currentTask.cancel();
        coolDown.purge();
        state = StateEngine.Blocked;
    }

    @Override
    public void stopNextFloor() {
        if(state != StateEngine.Stopped)
            _currentTask.cancel();

        new Thread(()->{
            var nextF = chooseNextFloor();
            simulateSlowDescent(nextF, mustUpdateFloor(nextF));
            }).start();
    }

    int chooseNextFloor(){
        var nextFloor = 0;
        if(state == StateEngine.goingUp)
            nextFloor = _currentFloor + 1;
        else if(state == StateEngine.goingDown)
            nextFloor = _currentFloor;
        assert(nextFloor >=0);
        return  nextFloor;
    }

    boolean mustUpdateFloor(int nextF){
        if(nextF == _currentFloor)
            return false;
        return true;
    }

    void simulateSlowDescent(int nextF, boolean updateFloor){
        var distanceRemaining =Math.abs(nextF * _distanceBtwFloor - distanceEllapsed);
        var time = distanceRemaining / _approachSpeed;
        System.out.println("Temps restant :" + time);
        try{
            TimeUnit.MILLISECONDS.sleep((long)time);
        }
        catch (Exception e){e.printStackTrace();}
        updateElevatorState(updateFloor, distanceRemaining);
    }

    void updateElevatorState(boolean updateFloor, double distanceTraveled){
        if(updateFloor)
            updateFloor();
        distanceEllapsed += distanceTraveled;
        state = StateEngine.Stopped;
    }

    void updateFloor(){
        if(state == StateEngine.goingUp) {
            _currentFloor++;
            commandControl.updateFloor();
        }
        else if(state == StateEngine.goingDown ) {
            _currentFloor--;
            commandControl.updateFloor();
        }
        assert _currentFloor >= 0 : "Error current floor must be positive !";
    }

    //Pour tester le bon fonctionnement du matériel
    public StateEngine getStateEngine(){
        return state;
    }

    public void setCommandControl(CommandControl cc){
        commandControl = cc;
    }


}
