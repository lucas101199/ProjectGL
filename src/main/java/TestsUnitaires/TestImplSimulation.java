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
        elevator = new ImplPourSimulation(0.5,0.3,5);
        cc = new FakeCommandControl(elevator);
    }


    @Test
    public void testUp(){
        elevator.Up();
        Assert.assertEquals(StateEngine.goingUp, elevator.getStateEngine());
        elevator.Down();
        Assert.assertEquals(StateEngine.Stopped, elevator.getStateEngine());
    }

    @Test
    public void testDown(){
        elevator.Down();
        Assert.assertEquals(StateEngine.goingDown, elevator.getStateEngine());
        elevator.Up();
        Assert.assertEquals(StateEngine.Stopped, elevator.getStateEngine());
    }

    @Test
    public void testStopNextFloor(){
       /*
        elevator.stopNextFloor();
        waitFor(5);
        Assert.assertEquals(0, cc.getFloor());*/

        cc.setDirection(Direction.Up);  // To force floor counting
        elevator.Up();
        waitFor(8);
        elevator.stopNextFloor();
        waitFor(5);
        Assert.assertEquals(1, cc.getFloor());
    }

    public void waitFor(int sec){
        try { TimeUnit.SECONDS.sleep(sec); }
        catch(Exception e){e.printStackTrace();}
    }
}
