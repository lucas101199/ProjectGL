package TestsUnitaires;

import CC.FakeCommandControl;
import CC.Query;
import Operative.StateEngine;
import org.junit.*;
import Operative.ImplPourSimulation;


public class TestImplSimulation {

    @Test
    public void testUp() {
        var elevator = new ImplPourSimulation();
        elevator.Up();
        Assert.assertEquals(StateEngine.goingUp, elevator.getStateEngine());
        elevator.Down();
        Assert.assertEquals(StateEngine.Stopped, elevator.getStateEngine());

    }

    @Test
    public void testDown() {
        ImplPourSimulation elevator = new ImplPourSimulation();
        elevator.Down();
        Assert.assertEquals(StateEngine.goingDown, elevator.getStateEngine());
        elevator.Up();
        Assert.assertEquals(StateEngine.Stopped, elevator.getStateEngine());
    }

    /*@Test
    public void testStopNextFloor(){
        ImplPourSimulation elevator = new ImplPourSimulation();
        FakeCommandControl fakeControlCommand = new FakeCommandControl(elevator);
        Query queryNextFloor = new Query();
        queryNextFloor.addInstruction("Order", "NextFloor");
        fakeControlCommand.handleQuery(queryNextFloor);
        Assert.assertEquals(0, fakeControlCommand.getFloor());

        Query queryUp = new Query();
        queryUp.addInstruction("Order", "Up");
        fakeControlCommand.handleQuery(queryUp);
        fakeControlCommand.handleQuery(queryNextFloor);
        Assert.assertEquals(1, fakeControlCommand.getFloor());
    }*/
}
