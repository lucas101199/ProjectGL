package TestsUnitaires;
import CC.*;
import Operative.ImplPourSimulation;
import org.junit.*;

import java.util.concurrent.TimeUnit;

public class TestCCImpl {

    @Test
    public void testEmmergencyStop(){
        var elevator = new ImplPourSimulation(3,1,5,5,0);
        var cc = new CommandControlImpl(elevator, 0);

        cc.handleEvent(Event.EMMERGENCY_STOP);
        Assert.assertEquals(CCState.IS_BLOCKED, cc.getState());
    }

    @Test
    public void testSimpleUserUpRequestFromElevator(){
        var elevator = new ImplPourSimulation(3,2,5,5,0);
        var cc = new CommandControlImpl(elevator, 0);
        var strat = new StandartComp(cc);
        cc.setStrategy(strat);
        cc.handleEvent(Event.USER_REQUEST.linkQuery(new Query(1)));
        cc.handleEvent(Event.READY_TO_GO);

        waitFor(1);
        Assert.assertEquals(Direction.Up, cc.getDirection());
        Assert.assertEquals(CCState.IS_GOING_UP, cc.getState());
        waitFor(6);
        Assert.assertEquals(CCState.IS_STOPPED, cc.getState());
        Assert.assertEquals( 1, cc.getFloor());

        cc.handleEvent(Event.USER_REQUEST.linkQuery(new Query(3)));
        cc.handleEvent(Event.READY_TO_GO);

        waitFor(1);
        Assert.assertEquals(Direction.Up, cc.getDirection());
        Assert.assertEquals(CCState.IS_GOING_UP, cc.getState());
        waitFor(6);
        Assert.assertEquals(CCState.IS_STOPPED, cc.getState());
        Assert.assertEquals(3, cc.getFloor());
    }

    @Test
    public void testSimpleUserDownRequestFromElevator(){
        var elevator = new ImplPourSimulation(3,2,5,5,4);
        var cc = new CommandControlImpl(elevator, 4);
        var strat = new StandartComp(cc);
        cc.setStrategy(strat);
        cc.handleEvent(Event.USER_REQUEST.linkQuery(new Query(3)));
        cc.handleEvent(Event.READY_TO_GO);

        waitFor(1);
        Assert.assertEquals(Direction.Down, cc.getDirection());
        Assert.assertEquals(CCState.IS_GOING_DOWN, cc.getState());
        waitFor(6);
        Assert.assertEquals(CCState.IS_STOPPED, cc.getState());
        Assert.assertEquals( 3, cc.getFloor());

        cc.handleEvent(Event.USER_REQUEST.linkQuery(new Query(1)));
        cc.handleEvent(Event.READY_TO_GO);

        waitFor(1);
        Assert.assertEquals(Direction.Down, cc.getDirection());
        Assert.assertEquals(CCState.IS_GOING_DOWN, cc.getState());
        waitFor(6);
        Assert.assertEquals(CCState.IS_STOPPED, cc.getState());
        Assert.assertEquals(1, cc.getFloor());
    }


    @Test
    public void testTwoUpRequestFromElevator(){
        var elevator = new ImplPourSimulation(3,2,5,5,0);
        var cc = new CommandControlImpl(elevator, 0);
        var strat = new StandartComp(cc);
        cc.setStrategy(strat);
        cc.handleEvent(Event.USER_REQUEST.linkQuery(new Query(3)));
        cc.handleEvent(Event.USER_REQUEST.linkQuery(new Query(1)));
        cc.handleEvent(Event.READY_TO_GO);

        Assert.assertEquals(Direction.Up, cc.getDirection());
        Assert.assertEquals(CCState.IS_GOING_UP, cc.getState());
        waitFor(6);
        Assert.assertEquals(CCState.IS_STOPPED, cc.getState());
        Assert.assertEquals(1, cc.getFloor());

        cc.handleEvent(Event.READY_TO_GO);

        Assert.assertEquals(Direction.Up, cc.getDirection());
        Assert.assertEquals(CCState.IS_GOING_UP, cc.getState());
        waitFor(6);
        Assert.assertEquals(CCState.IS_STOPPED, cc.getState());
        Assert.assertEquals(3, cc.getFloor());

        cc.handleEvent(Event.READY_TO_GO);
        Assert.assertEquals(CCState.WAITING, cc.getState());
    }
    @Test
    public void testQueryPreemptionDuringClimbingFromElevator(){
        var elevator = new ImplPourSimulation(3,2,5,5,0);
        var cc = new CommandControlImpl(elevator, 0);
        var strat = new StandartComp(cc);
        cc.setStrategy(strat);
        cc.handleEvent(Event.USER_REQUEST.linkQuery(new Query(3)));
        cc.handleEvent(Event.READY_TO_GO);

        Assert.assertEquals(Direction.Up, cc.getDirection());
        waitFor(2);
        cc.handleEvent(Event.USER_REQUEST.linkQuery(new Query(2)));
        waitFor(4);
        Assert.assertEquals(CCState.IS_STOPPED, cc.getState());
        Assert.assertEquals(2, cc.getFloor());
    }

    public void waitFor(int sec){
        try { TimeUnit.SECONDS.sleep(sec); }
        catch(Exception e){e.printStackTrace();}
    }
}
