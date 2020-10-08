package CC;

import Operative.InterfaceMaterielle;

import java.util.Comparator;

public class StandartComp implements Comparator<Query> {
    private  CommandControl _cc;

    public StandartComp(CommandControl cc){
        _cc = cc;
    }


    @Override
    public int compare(Query query1, Query query2) {
        if(_cc.getDirection() == Direction.Up)
            return Integer.valueOf(query1.getFloor()).compareTo(Integer.valueOf(query2.getFloor()));
        else if(_cc.getDirection() == Direction.Down) {
            if(query1.getFloor() < query2.getFloor())
                return 1;
            else if(query1.getFloor() == query2.getFloor())
                return 0;
            else
                return -1;
        }
        else
            return 0;
    }
}
