package Operative;

import CC.CCState;
import CC.CommandControl;
import CC.Direction;
import CC.Event;

import java.util.Random;
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
    private boolean DoorsIsClosed;

    private class GoingUpDownAction extends TimerTask{

        /**
         * main function that make the simulate engine to work
         */
        @Override
        public void run() {
            if((distanceEllapsed < (1./3) * _distanceBtwFloor && state == StateEngine.goingDown )|| (distanceEllapsed > (_nbOfFloor-1)*_distanceBtwFloor
                                                                                   + (2./3)*_distanceBtwFloor && state == StateEngine.goingUp))
            {
                _currentTask.cancel();
                stopNextFloor();
            }
            if(state == StateEngine.goingUp)
                distanceEllapsed += _vSpeed * resfreshDelay;
            else if(state == StateEngine.goingDown)
                distanceEllapsed -= _vSpeed * resfreshDelay;
            System.out.println("Distance ellapsed :" + distanceEllapsed);
            if((int)(distanceEllapsed / _distanceBtwFloor) != _currentFloor)
                updateFloor();
        }
    }


    /**
     *
     * @param vSpeed
     * @param approachSpeed
     * @param distanceBetweenFloor
     * @param nbOfFloor
     * @param initialFloor
     * vSpeed, approachSpeed are in meters per second and must be positive.
     */
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
        DoorsIsClosed = true;
    }

    /**
     * Simulate the elevator going up
     */
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

    /**
     * Simulate the elevator going down
     */
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

    /**
     * Stop the engine and cancel the task
     */
    @Override
    public void emergencyStop() {
        _currentTask.cancel();
        coolDown.purge();
        state = StateEngine.Blocked;
    }

    /**
     * Stop the engine at the next floor
     */
    @Override
    public void stopNextFloor() {
        if(state != StateEngine.Stopped)
            _currentTask.cancel();

        new Thread(()->{
            var nextF = chooseNextFloor();
            simulateSlowDescent(nextF, mustUpdateFloor(nextF));
          /*  DoorsIsClosed = false;
            int rand = (int) (Math.random() * (7 - 3));
            try {
                Thread.sleep(rand);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            DoorsIsClosed = true;
            commandControl.handleEvent(Event.READY_TO_GO);*/
        }).start();
    }

    /**
     *
     * @return the number of floor
     */
    int chooseNextFloor(){
        int nextFloor = 0;
        if(state == StateEngine.goingUp)
            nextFloor = _currentFloor + 1;
        else if(state == StateEngine.goingDown)
            nextFloor = _currentFloor;
        assert(nextFloor >=0);
        return  nextFloor;
    }

    /**
     *
     * @param nextF
     * @return false if the next floor is equal to the current floor otherwise return true
     */
    boolean mustUpdateFloor(int nextF){
        if(nextF == _currentFloor)
            return false;
        return true;
    }

    /**
     *
     * @param nextF
     * @param updateFloor
     */
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

    /**
     *
     * @param updateFloor
     * @param distanceTraveled
     */
    void updateElevatorState(boolean updateFloor, double distanceTraveled){
        if(updateFloor)
            updateFloor();
        if(state == StateEngine.goingUp)
            distanceEllapsed += distanceTraveled;
        else if(state == StateEngine.goingDown)
            distanceEllapsed -= distanceTraveled;
        state = StateEngine.Stopped;
        commandControl.handleEvent(Event.STOPPED);

    }

    /**
     * increment or decrement the number of floor
     */
    void updateFloor(){
        System.out.println("send !");
        commandControl.handleEvent(Event.NEW_FLOOR);
        if(state == StateEngine.goingUp)
            _currentFloor++;
        else if(state == StateEngine.goingDown )
            _currentFloor--;

        assert _currentFloor >= 0 : "Error current floor must be positive !";
    }

    //Pour tester le bon fonctionnement du matériel
    /**
     *
     * @return StateEngine
     */
    public StateEngine getStateEngine(){
        return state;
    }

    /**
     *
     * @param cc
     */
    public void setCommandControl(CommandControl cc){
        commandControl = cc;
    }

    /**
     *
     * @return true if the door is closed
     */
    public boolean isDoorsIsClosed() {
        return DoorsIsClosed;
    }
}
