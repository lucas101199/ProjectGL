package TestsUnitaires;
import CC.CommandControl;
import CC.CommandControlImpl;
import CC.Event;
import CC.Query;
import Operative.ImplPourSimulation;
import org.junit.*;

public class TestCCImpl {
    @Test
    public void testFloor1Query(){
        var elevator = new ImplPourSimulation(3,1,5,5,0);
        var cc = new CommandControlImpl(elevator, 0);
        var ev = Event.USER_REQUEST.linkQuery(new Query(1));
        cc.handleEvent(ev);

    }
}
