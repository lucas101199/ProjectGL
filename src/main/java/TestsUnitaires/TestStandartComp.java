package TestsUnitaires;

import CC.Direction;
import CC.FakeCommandControl;
import CC.Query;
import CC.StandartComp;
import Operative.ImplPourSimulation;
import org.junit.Assert;
import org.junit.Test;

import java.util.PriorityQueue;

public class TestStandartComp {

    @Test
    public void testWith2Queries(){
        var elevator = new ImplPourSimulation(5,5,5,5,5);
        var cc = new FakeCommandControl(elevator,0);
        var comp = new StandartComp(cc);
        var queue = new PriorityQueue<Query>(comp);
        queue.add(new Query(1));
        queue.add(new Query(3));

        Assert.assertEquals( 1, queue.poll().getFloor());
        cc.setDirection(Direction.Up);
        queue.add(new Query(1));
        Assert.assertEquals(1, queue.poll().getFloor());

    }

}
