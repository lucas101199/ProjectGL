package TestsUnitaires;

import CC.Direction;
import CC.FakeCommandControl;
import CC.Query;
import Operative.StateEngine;
import org.junit.*;
import Operative.ImplPourSimulation;

import java.util.concurrent.TimeUnit;


public class TestImplSimulation {
    private ImplPourSimulation elevator;
    private FakeCommandControl cc;


    @Before
    public void reinit(){
        elevator = new ImplPourSimulation(0.5,0.3,5, 3, 0);
        cc = new FakeCommandControl(elevator, 0);
    }


    @Test
    public void testUp(){
        var elevator = new ImplPourSimulation(0.7, 0.5, 5, 3, 1);
        elevator.setCommandControl(cc);
        elevator.Up();
        Assert.assertEquals(StateEngine.goingUp, elevator.getStateEngine());
        elevator.Down();
        Assert.assertEquals(StateEngine.Stopped, elevator.getStateEngine());
    }

    @Test
    public void testDown(){
        var elevator = new ImplPourSimulation(0.7, 0.5, 5, 3, 1);
        elevator.setCommandControl(cc);
        elevator.Down();
        Assert.assertEquals(StateEngine.goingDown, elevator.getStateEngine());
        elevator.Up();
        Assert.assertEquals(StateEngine.Stopped, elevator.getStateEngine());
    }

    @Test
    public void testGoingToTouchFloor(){
        var elevator = new ImplPourSimulation(0.7, 0.5, 5, 3, 3);
        elevator.setCommandControl(cc);
        elevator.Up();
        Assert.assertEquals(StateEngine.Stopped, elevator.getStateEngine());

        elevator = new ImplPourSimulation(3, 0.5, 5, 3, 1);
        elevator.setCommandControl(cc);
        elevator.Down();
        waitFor(5);
        Assert.assertEquals(StateEngine.Stopped, elevator.getStateEngine());
        Assert.assertEquals(0, cc.getFloor());
    }

    @Test
    public void testGoingToTouchRoof(){
        var elevator = new ImplPourSimulation(0.7, 0.5, 5, 3, 0);
        var cc =new FakeCommandControl(elevator, 0);
        elevator.setCommandControl(cc);
        cc.setDirection(Direction.Down);
        elevator.Down();
        Assert.assertEquals(StateEngine.Stopped, elevator.getStateEngine());

        elevator = new ImplPourSimulation(3, 0.5, 5, 3, 2);
        cc = new FakeCommandControl(elevator, 2);
        elevator.setCommandControl(cc);
        cc.setDirection(Direction.Up);
        elevator.Up();
        waitFor(5);
        Assert.assertEquals(StateEngine.Stopped, elevator.getStateEngine());
        Assert.assertEquals(3, cc.getFloor());
    }

    @Test
    public void testStoppedThenNextFloor(){
        elevator.stopNextFloor();
        waitFor(5);
        Assert.assertEquals(0, cc.getFloor());
    }

    @Test
    public void testStopNextFloor(){
        cc.setDirection(Direction.Up);  // To force floor counting
        elevator.Up();
        waitFor(8);
        elevator.stopNextFloor();
        waitFor(5);
        Assert.assertEquals(1, cc.getFloor());
        Assert.assertEquals(StateEngine.Stopped, elevator.getStateEngine());

        cc.setDirection(Direction.Down);
        elevator.Down();
        waitFor(8);
        elevator.stopNextFloor();
        waitFor(5);
        Assert.assertEquals(0, cc.getFloor());
        Assert.assertEquals(StateEngine.Stopped, elevator.getStateEngine());
    }



    public void waitFor(int sec){
        try { TimeUnit.SECONDS.sleep(sec); }
        catch(Exception e){e.printStackTrace();}
    }
}
